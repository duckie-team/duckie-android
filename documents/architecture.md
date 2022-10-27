# Architecture

덕키에서 사용할 "덕키만의 아키텍처" 에 대해 서술합니다.

작성자: [Ji Sungbin](https://www.linkedin.com/in/ji-sungbin-4343b7219/)  
마지막 업데이트: 2022. 10. 28.

### 목차

1. [기본 컨셉](#Basic)

2. [제약](#Limitation)

3. [View](#View)

4. [Model](#Model)

5. [ViewModel](#ViewModel)
   1. [DataBinding](#DataBinding)
   - [AAC ViewModel](#AAC-ViewModel)

6. [UDF](#Unidirectional-Data-Flow)
   1. [Flux](#Flux)
   2. [Redux](#Redux)
   3. [Model-View-Intent](#Model-View-Intent)

7. [결론](#conclusion)

---

## Basic

덕키 아키텍처는 MVVM 과 UDF 에 중심을 두고 있습니다. 이때, 여기에서 의미하는 MVVM 는 Microsoft 가 제안한 "Model-View-ViewModel Pattern" 을 의미합니다. AAC 의 ViewModel 를 의미하지 않습니다.

##### Reference

- Microsoft: [The Model-View-ViewModel Pattern](https://learn.microsoft.com/en-us/xamarin/xamarin-forms/enterprise-application-patterns/mvvm)
- AAC: [ViewModel overview](https://developer.android.com/topic/libraries/architecture/viewmodel)

## Limitation

## View

## Model

## ViewModel

#### DataBinding

### AAC ViewModel

## Unidirectional Data Flow

#### Flux

#### Redux

#### Model-View-Intent

## Conclusion
