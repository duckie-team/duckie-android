# 덕키 아키텍처 제안서

작성자: [Ji Sungbin](https://www.linkedin.com/in/ji-sungbin-4343b7219/)  
마지막 업데이트: 2022. 10. 28.

### 목차

1. [제안서](#Proposal)
2. [예제 프로젝트](#ApiLibrary)

---

# Proposal

덕키에서 사용할 "덕키 아키텍처" 에 대해 제안합니다.

### 목차

1. [기본 컨셉](#Basic)
   1. [참고 자료](#reference)
2. [제약](#Limitation)
   1. [참고 자료](#reference-1)
3. [View](#View)
   1. [참고 자료](#reference-2)
4. [Model](#Model)
   1. [참고 자료](#reference-3)
5. [ViewModel](#ViewModel)
   1. [DataBinding](#DataBinding)
   2. [AAC ViewModel](#AAC-ViewModel)
   3. [참고 자료](#reference-4)
6. [UDF](#Unidirectional-Data-Flow)
   1. [Flux](#Flux)
   2. [Redux](#Redux)
   3. [Model-View-Intent](#Model-View-Intent)
   4. [참고 자료](#reference-5)
7. [결론](#conclusion)

## Basic

이 제안서는 MVVM 과 UDF 에 중심을 두고 있습니다. 이때, 여기에서 의미하는 MVVM 는 Microsoft 가 제안한 "Model-View-ViewModel Pattern" 을 의미합니다. VM 으로 AAC 의 ViewModel 을 의미하지 않습니다.

##### Reference

- [The Model-View-ViewModel Pattern](https://learn.microsoft.com/en-us/xamarin/xamarin-forms/enterprise-application-patterns/mvvm)
- [AAC ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)

## Limitation

덕키 아키텍처는 덕키 안드로이드 앱을 기준으로 고안되었습니다. 모든 경우에 적합하지 않을 수 있으며, 모든 경우에 테스트되지 않았습니다.

덕키 안드로이드 앱에서는 Jetpack Compose 를 사용합니다. 따라서 이 제안 역시 Jetpack Compose 사용 사례에 적합하며, 안드로이드 환경에서 Jetpack Compose 를 사용해야 이 아키텍처의 의도를 제대로 활용할 수 있습니다.

##### Reference

- [Jetpack Compose](https://developer.android.com/jetpack/compose)

## View

##### Reference

## Model

##### Reference

## ViewModel

#### DataBinding

#### AAC ViewModel

##### Reference

## Unidirectional Data Flow

#### Flux

#### Redux

#### Model-View-Intent

##### Reference

## Conclusion

[⬆ 처음으로](#덕키-아키텍처)

---

# ApiLibrary

이 제안서가 나타내는 아키텍처를 사용한 예제 프로젝트입니다.

### 목차

미정
