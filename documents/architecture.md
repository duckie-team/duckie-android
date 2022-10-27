# 덕키 아키텍처 제안서

작성자: [Ji Sungbin](https://www.linkedin.com/in/ji-sungbin-4343b7219/)  
마지막 업데이트: 2022. 10. 28.

### 목차

1. [규칙](#Prerequisites)
2. [제안서](#Proposal)
3. [예제 프로젝트](#ApiLibrary)

---

# Prerequisites

이 제안서에서는 정확한 의미 전달을 위해 해당 문맥에서 중요한 영문은 영문 그대로 표시합니다.

- 컴포저블 → Composable
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
   1. [Why?](#why-1)
   2. [참고 자료](#reference-3)
5. [ViewModel](#viewModel)
   1. [DataBinding](#databinding)
   2. [AAC ViewModel](#aac-viewmodel)
   3. [Why?](#why-2)
   4. [참고 자료](#reference-4)
6. [Composable is Reactive](#reactive-programming-in-composable)
	 1. [참고 자료](#reference-5)	
7. [Two-way binding](#two-way-binding)
   1. [참고 자료](#reference-6)	
8. [UDF](#unidirectional-data-flow)
   1. [Flux](#flux)
   2. [Redux](#redux)
   3. [Model-View-Intent](#model-view-intent)
   4. [참고 자료](#reference-7)
9. [결론](#conclusion)

## Basic

이 제안서는 MVVM 과 UDF 에 중심을 두고 있습니다. 이때, 여기에서 의미하는 MVVM 는 Microsoft 가 제안한 "Model-View-ViewModel" 을 의미합니다. VM 으로 AAC 의 ViewModel 을 의미하지 않습니다.

##### Reference

- [The Model-View-ViewModel Pattern](https://learn.microsoft.com/en-us/xamarin/xamarin-forms/enterprise-application-patterns/mvvm)
- [AAC ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)

## Limitation

덕키 아키텍처는 덕키 안드로이드 앱을 기준으로 고안되었습니다. 모든 경우에 적합하지 않을 수 있으며, 모든 경우에 검증되지 않았습니다.

덕키 안드로이드 앱에서는 Jetpack Compose 를 사용합니다. 따라서 이 제안 역시 Jetpack Compose 사용 사례에 적합하며, 안드로이드 환경에서 Jetpack Compose 를 사용해야 이 아키텍처의 의도를 제대로 활용할 수 있습니다.

##### Reference

- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html)

## View

View 는 Composable 을 통해 사용자와 상호작용을 담당합니다. 사용자와 상호작용 의외의 것은 View 가 알면 안됩니다. 예를 들어 business logic 은 View 가 절대 알아선 안됩니다. 이는 View 에 과한 역할을 부여합니다.

View 는 Presenter 계층에 포함될 수 있습니다.

#### Why

##### 사용자와 상호작용 의외의 것은 View 가 알면 안됩니다.

View 와 business logic 간의 관심사 분리를 위합니다. 관심사 분리로 얻는 이점은 다음과 같습니다.

1. View 개발자가 전체적인 코드 파악을 진행할 때 business logic 부분을 몰라도 되므로 효율성을 높입니다.
2. View 를 개발하는데 불필요한 codebase 를 제거해 View 개발자가 작업에 좀 더 집중할 수 있습니다
3. business logic 를 구현하는데 필요한 의존성을 포함하지 않으므로 빌드 속도를 단축시킵니다. (gradle 을 사용하는 멀티 모듈 프로젝트에 한함)
4. View 모듈이 business logic 의 구현 방법에 영향을 받지 않으므로 View 모듈을 여러 곳에서 재사용할 수 있습니다.

##### Reference

- [MVVM View](https://learn.microsoft.com/en-us/xamarin/xamarin-forms/enterprise-application-patterns/mvvm#view)
- [Separation of concerns](https://en.wikipedia.org/wiki/Separation_of_concerns)

## Model

#### Why

##### Reference

## ViewModel

#### DataBinding

#### AAC ViewModel

#### Why

##### Reference

## Reactive Programming in Composable

#### Reference

## Two-way binding

#### Reference

## Unidirectional Data Flow

#### Flux

#### Redux

#### Model-View-Intent

##### Reference

## Conclusion

[⬆ 처음으로](#덕키-아키텍처-제안서)

---

# ApiLibrary

이 제안서가 나타내는 아키텍처를 사용한 예제 프로젝트입니다.

### 목차

미정

[⬆ 처음으로](#덕키-아키텍처-제안서)
