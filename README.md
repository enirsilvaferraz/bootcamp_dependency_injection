# Bootcamp Dependency Injection

Implementar a injeção de dependências em um projeto android funcional.

Ao final da apresentação, você será capaz de entender a importância e como aplicar em seus projetos.

<BR>

## Desafio: Configurar a injeção de dependências automática

<BR>

### Parte 1: Aplicar a injeção de dependências manual

1. Passe todas as dependências das classes por parâmetro no construtor. Comece de trás para frente para facilitar o entendimento.

* UserRepositoryLocal
* UserRepositoryRemote
* GetUserUseCase
* CardViewModel

OBS.: As interfaces UserWebService e **UserDao** não precisam de alteração. Elas não possuem classes, dentro do nosso projeto, que implementem seu comportamento, são geradas pelo gradle (através do **Retrofit** e do **ROOM**) ao realizar o build. 


<BR>

2. Coloque todas as intâncias na classe **CardActivity**, por enquanto.

```kotlin
val database = Room.databaseBuilder(this, AppDataBase::class.java, "my-db").allowMainThreadQueries().build()
val userDao = database.userDao()
val repLocal = UserRepositoryLocal(userDao = userDao)

val retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
val userWebService = retrofit.create(UserWebService::class.java)
val repRemote = UserRepositoryRemote(userWebService = userWebService)

val getUserUseCase = GetUserUseCase(repLocal = repLocal, repRemote = repRemote)

viewModel = CardViewModel(getUserUseCase = getUserUseCase)
```

<BR>

3. Rode o app para garantir que tudo ainda está funcionando.

<BR>

## Parte 2: Aplicar a injeção de dependências automática

<BR>

1. Adicionar o Koin ao projeto (Já adicionei ao projeto para agilizar).

```groovy
// Koin for Kotlin apps
implementation "io.insert-koin:koin-android:3.1.2"
```

<BR>

2. Criar um arquivo chamado DI e mapeie, dentro dele, as instâncias das classes do projeto, conforme abaixo.


```kotlin
val koinModule = module {

    factory<AppDataBase> { 
        Room.databaseBuilder(androidContext(), AppDataBase::class.java, "my-db").allowMainThreadQueries().build() 
    }

    factory<UserDao> { get<AppDataBase>().userDao() }

    factory<UserRepositoryLocal> { UserRepositoryLocal(userDao = get<UserDao>()) }

    factory<Retrofit> { 
        Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build() 
    }

    factory<UserWebService> { get<Retrofit>().create(UserWebService::class.java) }

    factory<UserRepositoryRemote> { UserRepositoryRemote(userWebService = get<UserWebService>()) }

    factory<GetUserUseCase> { GetUserUseCase(repLocal = get<UserRepositoryLocal>(), repRemote = get<UserRepositoryRemote>()) }

    factory<CardViewModel> { CardViewModel(getUserUseCase = get<GetUserUseCase>()) }
}
```

<BR>

3. Inicie o Koin na activity, por enquanto.


```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    startKoin {
        androidContext(this@CardActivity)
        modules(koinModule)
    }

    observeLiveData()
    configureViews()
}
```

<BR>

4. Rode o app para garantir que tudo ainda está funcionando.

<BR>

## Parte 3: Refatorar a injeção de dependências automática

<BR>

1. Criar uma classe **AppApplication** que herde de **Application** e migre o start do Koin para ela. (Não se esquça de mapear a AppApplication no AndroidManigest)

2. Remover classes inferidas pelo kotlin no arquivo de DI.

3. Testar as funções **inject** e **get** na classe **CardActivity**.
* Inject para atributos de classe.
* get para variáveis dentro dos métodos.

4. Configurar as intâncias de **ROOM** e **Retrofit** para se tornarem **singletons** (use o método **single** do Koin).

5. Configurar a instância de **CardViewModel** com o método **viewModel** do Koin.

<BR>

OBS.: O código final da classe DI deve parecer com esse:

```kotlin
val koinModule = module {

    single { Room.databaseBuilder(androidContext(), AppDataBase::class.java, "my-db").allowMainThreadQueries().build() }

    factory { get<AppDataBase>().userDao() }

    factory { UserRepositoryLocal(userDao = get()) }

    single { Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build() }

    factory { get<Retrofit>().create(UserWebService::class.java) }

    factory { UserRepositoryRemote(userWebService = get()) }

    factory { GetUserUseCase(repLocal = get<UserRepositoryLocal>(), repRemote = get<UserRepositoryRemote>()) }

    viewModel { CardViewModel(getUserUseCase = get()) }
}
```