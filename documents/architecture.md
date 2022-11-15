# 덕키 아키텍처 제안서

작성자: [Ji Sungbin](https://www.linkedin.com/in/ji-sungbin-4343b7219/)  
본문 마지막 업데이트: 2022. 10. 30.  
상태: 승인

### 목차

1. [규칙](#Prerequisites)
2. [제안서](#Proposal)
3. [예제 프로젝트](#ApiLibrary)

---

# Prerequisites

이 제안서에서는 정확한 의미 전달을 위해 해당 문맥에서 중요한 영문은 영문 그대로 표시합니다.

- 컴포저블 → Composable
- 컴포지션 → Composition
- 리포지토리 → Repository
- 유즈케이스 → Usecase
- ... 등등

---

# Proposal

덕키에서 사용할 "덕키 아키텍처" 에 대해 제안합니다.

### 목차

1. [기본 컨셉](#basic)
   1. [참고 자료](#reference)
2. [제약](#limitation)
   1. [참고 자료](#reference-1)
3. [View](#view)
   1. [Why?](#why)
   2. [참고 자료](#reference-2)
4. [Model](#model)
   1. [Datasource](#datasource)
   2. [Repository](#repository)
   3. [Usecase](#usecase)
   4. [Why?](#why-1)
   5. [참고 자료](#reference-3)
5. [ViewModel](#viewModel)
   1. [Data binding](#data-binding)
   2. [AAC ViewModel](#aac-viewmodel)
   3. [Why?](#why-2)
   4. [참고 자료](#reference-4)
6. [Jetpack Compose 는 반응형임](#reactive-programming-in-jetpack-compose)
	 1. [참고 자료](#reference-5)	
7. [Two-way binding](#two-way-binding)
   1. [참고 자료](#reference-6)	
8. [UDF](#unidirectional-data-flow)
   1. [Why?](#why-3)
   2. [참고 자료](#reference-7)
9. [결론](#conclusion)

## Basic

이 제안서는 MVVM pattern, Repository pattern, Clean Architecture, Data binding, Jetpack Compose, Android(lifecycle 및 configuration change) 의 개념을 토대로 고안되었습니다.

#### 주의

1. 여기에서 의미하는 MVVM 는 Microsoft 가 제안한 "Model-View-ViewModel" 을 의미합니다. 즉, MVVM 의 ViewModel 은 AAC 의 ViewModel 을 의미하지 않습니다. 
2. 여기에서 의미하는 Data binding 은 AAC 와 MVVM(Xamarin) 의 Data binding 을 의미하지 않습니다. Computer programming 에서 사용되는 대중적인 Data binding 을 의미합니다.
3. 여기에서 의미하는 Jetpack Compose 는 Jetpack Compose UI 가 아닌 Jetpack Compose Runtime 을 의미합니다.

##### Reference

- [The Model-View-ViewModel Pattern](https://learn.microsoft.com/en-us/xamarin/xamarin-forms/enterprise-application-patterns/mvvm)
- [The Repository pattern](https://learn.microsoft.com/en-us/dotnet/architecture/microservices/microservice-ddd-cqrs-patterns/infrastructure-persistence-layer-design#the-repository-pattern)
- [The Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Data binding](https://en.wikipedia.org/wiki/Data_binding)
- [Jetpack Compose Runtime](https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/runtime/runtime/src/commonMain/kotlin/androidx/compose/runtime/compose-runtime-documentation.md)
- [Android lifecycle](https://developer.android.com/guide/components/activities/activity-lifecycle)
- [Android configuration change](https://developer.android.com/guide/topics/resources/runtime-changes)

## Limitation

이 아키텍처는 Jetpack Compose 를 사용하는 안드로이드 애플리케이션을 기준으로 고안되었습니다. 또한 모든 경우에서 충분한 검증을 거치지 않았으며, 모든 경우에서 부적합할 수 있습니다.

##### Reference

- [Jetpack Compose](https://developer.android.com/jetpack/compose)

## View

View 는 Composable 을 통해 사용자와 상호작용을 담당합니다. 사용자와 상호작용 의외의 것은 View 가 알면 안됩니다. 예를 들어 business logic 은 View 가 절대 알아선 안됩니다. 이는 View 에 과한 역할을 부여합니다.

View 는 Presentation 계층에 포함될 수 있습니다.

#### Why

> 사용자와 상호작용 의외의 것은 View 가 알면 안됩니다.

View 와 business logic 간의 관심사 분리를 위합니다. 관심사 분리로 얻는 이점은 다음과 같습니다.

1. View 개발자가 전체적인 코드 파악을 진행할 때 business logic 부분을 몰라도 되므로 효율성을 높입니다.
2. View 를 개발하는데 불필요한 codebase 를 제거해 View 개발자가 작업에 좀 더 집중할 수 있습니다.
3. business logic 를 구현하는데 필요한 의존성을 포함하지 않으므로 빌드 속도를 단축시킵니다. (멀티 모듈 프로젝트에 한함)
4. View 모듈이 business logic 의 구현 방법에 영향을 받지 않으므로 View 모듈을 여러 곳에서 재사용할 수 있습니다.

##### Reference

- [MVVM View](https://learn.microsoft.com/en-us/xamarin/xamarin-forms/enterprise-application-patterns/mvvm#view)
- [Separation of concerns](https://en.wikipedia.org/wiki/Separation_of_concerns)

## Model

Model 은 business logic 의 실제 구현을 나타냅니다. Datasource, Repository, Usecase 에서 상호작용될 수 있으며, 독립적으로 바로 사용하는게 아닌 최종적으로 Usecase 를 통해 사용되야 합니다.

Model 은 Domain 과 Data 계층에 포함될 수 있습니다.

#### Datasource

Datasource 는 business logic 에서 데이터의 출처를 정의합니다. Datsource 는 필요에 따라 remote 와 local 로 나뉠 수 있습니다. 이 경우 remote 는 온라인에서의 데이터 출처를 의미하고, local 은 오프라인에서의 데이터 출처를 의미합니다.

예를 들어 remote datasource 에서는 Ktor client 를 통해 데이터를 불러오고, local datasource 에서는 Room 을 통해 데이터를 불러올 수 있습니다.

#### Repository

Repository 는 같은 도메인을 나타내는 business logic 의 모임입니다.

```kotlin
// # example
// file: UserRepository.kt

interface UserRepository {
    fun changeUserName(newName: String): Boolean
    fun changeUserPhoto(newPhoto: Bitmap): Boolean
    fun changeUserAge(newAge: Int): Boolean
}
```

만약 특정 도메인에 포함되는 business logic 이 하나 뿐이라도 다른 Repository 와 통일성을 위해 Repository 에 제공해야 합니다.

```kotlin
// # example
// file: CatRepository.kt

interface CatRepository {
    fun giveLove()
}
```

또한 상황에 따라 사용해야 하는 Datasource 를 결정하여 제공하는 캡슐화를 담당합니다.

예를 들어 remote datasource 와 local datasoure 가 있다면 디바이스의 인터넷 여부에 따라 둘 중 하나를 결정하여 위임한 결과를 제공합니다.

#### Usecase

Usecase 는 Repository 로 부터 받은 결과를 View 가 사용하기 편하게 변조하거나 UI State 로 mapping 하는 역할을 담당합니다. 즉, Repository 의 결과를 View 가 바로 사용할 수 있게 추상화합니다.

Usecase 는 View 가 바로 사용할 수 있게 만드는 추상화 의외에도 한 번에 하나의 business logic 만 노출시켜 Usecase 를 사용하는 부분의 역할을 분명하게 나타낼 수 있습니다. 이러한 이유로 별도 Side effect 처리가 필요 없는 business logic 일 경우에도 Usecase 를 제공해야 합니다.

#### Why

> 독립적으로 바로 사용하는게 아닌 최종적으로 Usecase 를 통해 사용되야 합니다.

Usecase 는 Usecase 세션에서 설명하고 있듯이 Repository 의 결과로 부터 추상화를 제공하고 사용 역할을 분명하게 만들어 줍니다. Model 을 직접 다루게 되면 수행하고자 하는 business logic 과 추가로 필요한 Side effect 의 구현이 혼재되어 해당 Model 의 의도를 파악하기 어렵고 유지보수하기 어렵게 만들 수 있습니다.

##### Reference

- [MVVM Model](https://learn.microsoft.com/en-us/xamarin/xamarin-forms/enterprise-application-patterns/mvvm#model)
- [Side effect](https://en.wikipedia.org/wiki/Side_effect_(computer_science))

## ViewModel

ViewModel 은 View 와 Model 을 이어주는 중개자 역할을 담당합니다. View 에서 특정 Model 을 표시하는데 필요한 상태(ex_UI State... etc)들을 저장하고, Usecase 를 통해 특정 상태를 업데이트할 수 있습니다.

ViewModel 은 Presentation 계층에 포함될 수 있습니다.

#### Data binding

Data binding 은 View 와 Model 간에 상태를 동기화하는 역할을 담당합니다. One-way binding 과 Two-way binding 으로 구현될 수 있으며, 이 제안서에서는 One-way binding, 즉 Unidirectional Data Flow 를 사용합니다.

자세한 내용은 각각 세부 파트를 참고해 주세요.

#### AAC ViewModel

우리는 그동안 안드로이드에서 AAC ViewModel 을 사용해 왔습니다. AAC ViewModel 을 통해 얻을 수 있었던 이점은 configuration change 로 부터 객체 재생성이 일어나지 않고, 그대로 보존된다는 점이였습니다. 따라서 AAC ViewModel 을 사용하여 객체를 저장하면, configuration change 에 의해 Activity 의 recreation 이 일어나도 객체들은 재생성되지 않고 오직 새로운 configuration 만 반영됩니다.

Jetpack Compose 에서 모든 상태는 반응형입니다. 즉, configuration 또한 상태이기 때문에 우리가 직접 configuration change 를 신경쓰지 않아도 항상 최신 상태의 configuration 을 사용합니다. 따라서 Activity 의 configuration 제어권을 가져와서 configuration change 가 일어나도 Activity 의 recreation 이 일어나지 않게 하면, configuration change 가 일어나도 객체들이 재생성되지 않고 AAC ViewModel 을 사용한 것과 동일한 효과를 볼 수 있습니다. 자세한 내용은 "Reactive Programming in Jetpack Compose" 파트를 참고해 주세요.

이러한 이유로 AAC ViewModel 을 사용하지 않고 ViewModel 자체만 사용합니다.

#### Why

> 이러한 이유로 AAC ViewModel 을 사용하지 않고 ViewModel 자체만 사용합니다.

추가적인 이점을 서술합니다. AAC ViewModel 을 상속한 ViewModel 은 안드로이드에 의존적인 상태가 됩니다. 따라서 Unit Test 가 힘들어지며, 재사용에 제약이 있습니다.

하지만 AAC ViewModel 이 아닌 일반 ViewModel 을 사용하게 되면 안드로이드에 의존적인 상태가 사라지므로 앞써 말한 단점들도 사라집니다.

##### Reference

- [MVVM ViewModel](https://learn.microsoft.com/en-us/xamarin/xamarin-forms/enterprise-application-patterns/mvvm#viewmodel)
- [AAC ViewModel shouldn't be necessary with Compose.](https://twitter.com/JimSproch/status/1454620401934364679?s=20&t=a5EG8ssVDQfy7f1z8O7uzQ)
- [I am glad that Compose was conceived in such a way that made the AAC ViewModel useless.](https://twitter.com/dbaroncellimob/status/1561037972526481411?s=20&t=a5EG8ssVDQfy7f1z8O7uzQ)

## Reactive Programming in Jetpack Compose

> **Note**: 이 파트는 덕키 아키텍처의 내용이 아닌, AAC ViewModel 세션을 보충하기 위한 파트입니다.

Jetpack Compose 에서 모든 상태는 Snapshot System 에 의해 관리되며, Snapshot System 은 반응형 프로그래밍으로 개발됐습니다.

Jetpack Compose 에서 현재 configuration 을 얻어오는 수단인 `LocalConfiguration` 역시 Snapshot System 에 의해 관리되기 때문에 configuration change 가 일어나면 configuration 에 영향을 받는 Composable 들이 새로운 configuration 에 맞게 Recomposition 됩니다.

예를 들어 locale 이 변경됐다면 `stringResource` 을 사용중인 Composable 들이 새로운 locale 에 맞게 Recomposition 되고, orientation 에 따라 레이아웃을 동적으로 그리는 Composable 이 있다면 새로운 orientation 에 맞게 레이아웃을 다시 그립니다(Recomposition 을 수행합니다).

Jetpack Compose 를 사용하지 않는 안드로이드에서는 configuration change 를 대응하기 위해 Activity 를 recreation 하여 시스템이 자체적으로 새로운 configuration 에 맞게 처리하도록 해야 했습니다. 하지만 이렇듯 Jetpack Compose 를 사용하는 안드로이드는 configuration change 가 개발자의 수고 없이 반응형으로 처리되므로 Activity 의 recreation 을 필요로 하지 않습니다.

#### Reference

- [Reactive programming](https://en.wikipedia.org/wiki/Reactive_programming)
- [Jetpack Compose Snapshot System](https://dev.to/zachklipp/introduction-to-the-compose-snapshot-system-19cn)
- [With Compose, the UI updates automatically (with some exceptions) without effort from the developer.](https://twitter.com/JordiSaumell1/status/1585205514824077312?s=20&t=Jdy6TsjAYVY9qcy1f3SRhA)
- [The ideal solution is for your Activity to handle all config changes (rather than recreating)](https://twitter.com/chrisbanes/status/1395108877251788800?s=20&t=VbP3J6l4I8VHCjzeyAOWEQ)

## Two-way binding

> **Note**: 이 파트는 덕키 아키텍처의 내용이 아닌, UDF 의 이해를 돕기 위한 파트입니다.

Two-way binding 은 Model 의 상태를 변화시키는 출처에 제한을 두지 않습니다. 따라서 Model 에서 View 의 상태를 변화시킬 수 있고, View 에서도 Model 의 상태를 변화시킬 수 있습니다.

<image alt="two-way-binding" src="art/two-way-binding.svg" width="70%" />

이 방식을 사용하면 개발자의 수고 없이 View 와 Model 간의 상태가 항상 동기화된다는 장점이 있지만, 상태가 mutable 하고 상태의 변경이 여러 곳에서 발생할 수 있어서 코드의 예측과 디버깅을 어렵게 만든다는 단점이 있습니다. 이러한 단점이 있어서 이 아키텍처에서는 UDF 를 사용할 것을 제안합니다.

#### Reference

- [Data Binding](https://docs.angularjs.org/guide/databinding)

## Unidirectional Data Flow

Unidirectional Data Flow, 줄여서 UDF 는 One-way binding 이라고도 불리며, Model 의 상태를 변화시키는 출처를 단 한 곳으로 제안합니다. 따라서 Model 에서만 View 의 상태를 변화시키는 형태가 됩니다.

<image alt="one-way-binding" src="art/one-way-binding.svg" width="70%" />

UDF 를 달성하기 위해선 다음과 같은 조건이 필요합니다.

- 상태가 불변으로 관리돼야 합니다.
- 상태를 변경하는 API 가 ViewModel 에 은닉돼야 합니다. 
- 상태의 변경이 한 곳에서만 진행돼야 합니다.

위 조건을 모두 지키지 않으면 상태를 어떠한 곳에서든 변경할 수 있으므로 UDF 에 적합하지 않고, 만약 이를 모두 지킨다면 UDF 라고 볼 수 있습니다.

Jetpack Compose 에서 Recomposition 을 유발하기 위한 유일한 조건은 해당 Composable 이 참조하고 있는 상태의 변경입니다. 이 조건 때문에 UI 코드베이스의 대부분에서 상태가 직접 상호작용됩니다. 즉, 상태가 복잡하게 관리될 수 있습니다. 이런 환경에서 Two-way binding 을 사용한다면 상태 관리가 힘들어질 수 있으므로 UDF 를 통해 상태를 관리해야 합니다.

UDF 를 구현하는 대표적인 방법으론 Flux, Redux, Model-View-Intent 가 있습니다. 덕키 아키텍처는 사전에 정의된 구현법을 사용하는게 아닌, UDF 조건에 맞춰 직접 개발합니다.

####  Why

> 덕키 아키텍처는 사전에 정의된 구현법을 사용하는게 아닌, UDF 조건에 맞춰 직접 개발합니다.

UDF 는 상태를 다루는 방법론중 하나이고, 만족하기 위한 조건이 어렵지 않습니다. UDF 만족을 위해 이미 개발된 라이브러리를 그대로 사용하는건 과한 리소스일 수 있으며, 충분한 메리트가 존재하지 않습니다.

##### Reference

- [Uni-Directional Architecture on Android Using Realm](https://academy.realm.io/posts/eric-maxwell-uni-directional-architecture-android-using-realm/)
- [Flux](http://facebook.github.io/flux/)
- [Redux](https://redux.js.org/)
- [Model-View-Intent](https://hannesdorfmann.com/android/mosby3-mvi-1/)

## Conclusion

이 아키텍처는 AAC ViewModel 을 사용하지 않는 MVVM 을 기본 아키텍처로 가져가며, View 와 Model 간의 상태 관리를 위해 UDF 기법을 사용할 것을 제안합니다. 

최종적인 모습은 다음과 같습니다.

<image alt="conclusion" src="art/conclusion.svg" width="50%" />

이로부터 얻을 수 있는 대표적인 이점은 다음과 같습니다.

1. 각각 계층간의 관심사 분리
2. 플랫폼에 의존적이지 않은 ViewModel, Data, Domain
3. Testable 한 계층
4. 코드의 자유로운 재사용성
5. 복잡한 상황으로 부터 쉬운 상태 관리

[⬆ 처음으로](#덕키-아키텍처-제안서)

---

# ApiLibrary

이 제안서가 나타내는 아키텍처를 사용한 예제 프로젝트입니다. 

해당 프로젝트는 [duckie-team/ApiLibrary](https://github.com/duckie-team/ApiLibrary) 에서 확인할 수 있습니다.

### 목차

1. [domain 계층](#domain-layer)
   1. [Datasource](#datasource-1)
   2. [Repository](#repository-1)
   3. [Usecase](#usecase-1)
2. [data 계층](#data-layer)
   1. [Remote Datasource](#remote-datasource)
   2. [Local Datasource](#local-datasource)
   3. [RepositoryImpl](#repository)
3. [app 계층](#app-layer)
4. [presentation 계층](#presentation-layer)
   1. [ViewModel](#mainviewmodel)
   2. [Ui State](#apilibrarystate)
5. [테스트](#test)
   1. [data 계층 테스트](#data-layer-1)
   2. [presentation 계층 테스트](#presentation-layer-1) 

## Domain Layer

domain 계층에서는 구현 은닉을 위한 인터페이스들을 정의합니다. 구현을 은닉하면 사용자로 부터 의도치 않은 불법적인 접근을 막을 수 있습니다.

#### Datasource

Api 목록을 상황에 맞게 온오프라인으로 조회하고 오프라인에 저장할 방법을 정의합니다. 간단히 2개의 함수로 구성돼 있습니다.

- `suspend fun fetchAllApis(): List<ApiItem>`
- `suspend fun saveAllApis(apis: List<ApiItem>)`

#### Repository

`ApiLibraryDatasource` 를 통해 Api 를 받아올 방법을 정의합니다. 하나의 함수로 구성돼 있습니다.

- `suspend fun fetchAllApis(): List<ApiItem>`

#### Usecase

Repository 의 `fetchAllApis()` 결과를 presentation 계층에서 바로 사용할 수 있게 `Result<T>` 로 래핑 및 이름 순으로 정렬을 진행합니다.

## Data Layer

domain 계층에서 미리 정의한 인터페이스들을 실제로 구현합니다. presentation 계층에서는 domain 타입으로 선언하고 data 계층을 통해 초기화를 하여 사용합니다.

```kotlin
val repository: DomainType = DataInit()
```

#### Remote Datasource

온라인에서 데이터를 조회하는 방법을 구현합니다. 

#### Local Datasource

오프라인에서 데이터를 불러오고, 데이터를 저장하는 방법을 구현합니다.

#### RepositoryImpl

기기가 인터넷에 연결돼 있는지에 따라 Remote Datasource 를 사용할지, Local Datasource 를 사용할지 결정한 후에 해당 Datasource 로 요청을 위임합니다.

안드로이드에서 인터넷 연결 여부를 조회하기 위해선 안드로이드 프레임워크에 종속적인 `Context` 객체가 필요합니다. RepositoryImpl 에서는 테스트 가능성을 위해 해당 값을 `Context` 를 통해 직접 계산하는게 아닌, 생성자의 `Boolean` 인자로 받습니다.

만약 기기가 인터넷에 연결돼 있지 않다면 Local Datasource 를 이용해 오프라인에서 값을 가져오고, 인터넷에 연결돼 있다면 Remote Datasource 를 이용해 온라인에서 값을 가져온 후 Local Datasource 를 통해 데이터를 오프라인에 저장시킵니다.

## App Layer

app 계층에서는 애플리케이션을 사용할 준비를 합니다.

간단하게 `@HiltAndroidApp` 를 통해 Hilt 를 초기화시키는 클래스만 존재합니다.

## Presentation Layer

presentation 계층은 Composable 을 통해 실제로 UI 를 그리는 계층이며 비즈니스 로직을 포함하지 않습니다. 

원칙상 이 계층에서는 어떠한 비즈니스 로직도 포함되면 안되지만, 현재 ApiLibrary 의 구현에는 일부 예외가 존재합니다. 

RepositoryImpl 을 초기화하는 과정에서 기기의 인터넷 연결 여부를 받게 되는데, 인터넷 연결 여부를 구하기 위해 비즈니스 로직이 사용됩니다. 이 예외를 추후 해결해야 합니다.

#### MainViewModel

Model 과 Presentation 을 연결해주는 순수한 ViewModel 이며, 생성자로 Usecase 하나만 받아 모든 로직을 처리합니다. 

Api 를 요청하는 함수 하나로 구성돼 있습니다.

- `suspend fun loadApis()`

#### ApiLibraryState

presentation 이 표시할 UI 의 상태를 나타냅니다.

MainViewModel 에서만 수정이 가능한 UDF 형태로 상호 작용됩니다.

## Test

ApiLibrary 는 핵심 비즈니스 로직들의 테스트를 진행합니다.

#### Data Layer

data 계층에서는 unit test 를 통해 Remote Datasource 의 테스트를 진행하고, instrumentation test 를 통해 Local Datasource 의 테스트를 진행하고 있습니다. Remote Datasource 와 Local Datasource 가 모두 같은 결과를 반환하는지를 중점으로 테스트 합니다.

#### Presentation Layer

presentation 계층에서는 unit test 를 통해 MainViewModel 의 테스트를 진행하고 있습니다. ViewModel 의 Ui State 가 의도한대로 잘 흘러가는지를 중점으로 테스트 합니다.

[⬆ 처음으로](#덕키-아키텍처-제안서)
