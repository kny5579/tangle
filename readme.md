# 주문 관리 데스크톱 프로그램

👑 우리 아빠를 위한 맞춤 프로그램👑 </br>
JavaFX 기반으로 제작된 설치형 주문 관리 애플리케이션입니다.   </br>
엑셀로 일일이 입력하던 주문 과정을 자동화하여, 입력 → 확인 → 저장을 효율적으로 처리할 수 있습니다. </br>

---

## 📌 주요 기능

- ✅ 주문 정보 입력 (이름, 주소, 전화번호, 수량, 상품명, 보내는 사람 정보 등)
- ✅ 실시간 테이블 표시 (추가한 주문 정보가 화면에 누적 표시됨)
- ✅ 엑셀 저장 기능 (`주문내역.xlsx` 파일 생성)
- ✅ 입력창 자동 초기화
- ✅ .exe로 개별 컴퓨터에서 설치 후 실행
- ⏳ 구현 중: 자동완성, 수정/삭제, 기존 엑셀 불러오기 기능(폴더구조 도입), UI 구현

---

## ⚙️ 기술 스택

| 항목         | 사용 기술                         |
|--------------|-----------------------------------|
| Language     | Java 17                           |
| UI Framework | JavaFX 21, ControlsFX             |
| Database     | SQLite                            |
| Excel        | Apache POI 5.2.3                  |
| Build Tool   | Gradle                            |


---

## 🚀 실행 방법

1. Java 17 이상 설치
2. 터미널 또는 IntelliJ에서 Gradle로 실행:

```bash
./gradlew run
