# SPRING PLUS

## Level. 1
### 1. 코드 개선 퀴즈 - @Transactional의 이해
- 할 일 저장 기능을 구현한 API(/todos)를 호출할 때, 아래와 같은 에러가 발생하고 있어요.
- 에러가 발생하지 않고 정상적으로 할 일을 저장할 수 있도록 코드를 수정해주세요.
![image](https://github.com/user-attachments/assets/85727ea9-055e-4b96-9c3c-75c7dd02e6d8)

TodoService 전체에 @Transactional(readOnly = true)가 설정되어 있다.
하지만 saveTodo에서는 DB에 쓰는 작업이 필요하기 때문에 readOnly = true가 되면 안된다.
나머지는 readOnly = true가 필요하기 때문에 TodoService 전체에 걸려있는 @Transactional(readOnly = true)를 각 Method에 달아주고, saveTodo Method에는 @Transactional로 설정해준다.

### 2. 코드 추가 퀴즈 - JWT의 이해
- User의 정보에 nickname이 필요해졌어요.
    - User 테이블에 nickname 컬럼을 추가해주세요.
    - nickname은 중복 가능합니다.
- 프론트엔드 개발자가 JWT에서 유저의 닉네임을 꺼내 화면에 보여주길 원하고 있어요.

먼저 User Entity에 nickname Field를 추가해주고, JWT에도 nickname Field를 추가해주기 위해 AuthUserArgumentResolver, JwtFilter, JwtUtill도 nickname을 포함할 수 있도록 수정해준다. 그리고 나머지에서도 필요한 곳을 수정해준다.

### 3. 코드 개선 퀴즈 - JPA의 이해
- 할 일 검색 시 `weather` 조건으로도 검색할 수 있어야해요.
    - `weather` 조건은 있을 수도 있고, 없을 수도 있어요!
- 할 일 검색 시 수정일 기준으로 기간 검색이 가능해야해요.
    - 기간의 시작과 끝 조건은 있을 수도 있고, 없을 수도 있어요!
- JPQL을 사용하고, 쿼리 메소드명은 자유롭게 지정하되 너무 길지 않게 해주세요.

TodoController에서 전달받을 RequestParam으로 weather, startDate, endDate를 추가해주고,
TodoService에서는 weather가 빈 경우와 아닌 경우로 나눠서 TodoRepository에 Todo를 요청한다.
weather가 빈 경우, findAllByDate Method, 아닌 경우, findAllByWeatherAndDate로 Todo를 조회한다.

### 4. 테스트 코드 퀴즈 - 컨트롤러 테스트의 이해
- 테스트 패키지 org.example.expert.domain.todo.controller의 todo_단건_조회_시_todo가_존재하지_않아_예외가_발생한다() 테스트가 실패하고 있어요.
- 테스트가 정상적으로 수행되어 통과할 수 있도록 테스트 코드를 수정해주세요.

예외가 발생했는데 status()로 isOk()를 반환하고 있으므로, 적절하게 isBadRequest()로 수정해주고, 아래의 HttpStatus도 OK에서 BAD_REQUEST로 수정해준다.

### 5. 코드 개선 퀴즈 - AOP의 이해
- `UserAdminController` 클래스의 `changeUserRole()` 메소드가 실행 전 동작해야해요.
- `AdminAccessLoggingAspect` 클래스에 있는 AOP가 개발 의도에 맞도록 코드를 수정해주세요.

실행 전에 동작해야하는데 @After로 설정되어 있고, 수행되는 Controller와 Method명이 적절하지 않으므로 @Before와 UserAdminController.changeUserRole, logBeforeChangeUserRole로 수정해준다.

## Level. 2
### 6. JPA Cascade
- 할 일을 새로 저장할 시, 할 일을 생성한 유저는 담당자로 자동 등록되어야 합니다.
- JPA의 `cascade` 기능을 활용해 할 일을 생성한 유저가 담당자로 등록될 수 있게 해주세요.

@OneToMany(mappedBy = "todo")에 cascade = CascadeType.PERSIST를 추가해주어 할 일을 새로 저장할 시, 할 일을 생성한 유저는 담당자로 자동 등록하도록 설정한다.

### 7. N + 1
- `CommentController` 클래스의 `getComments()` API를 호출할 때 N+1 문제가 발생하고 있어요. N+1 문제란, 데이터베이스 쿼리 성능 저하를 일으키는 대표적인 문제 중 하나로, 특히 연관된 엔티티를 조회할 때 발생해요.
- 해당 문제가 발생하지 않도록 코드를 수정해주세요.

JOIN을 JOIN FETCH로 바꾸어 Fetch Join을 사용해 N + 1 문제를 해결한다. 또는 @EntityGraph(attributePaths = {"user"})를 사용해서 N + 1 문제를 해결할 수 있다. 하지만 이를 사용하는 경우 메서드명에서 WithUser를 지워주어야 동작한다.

### 8. Query DSL
- JPQL로 작성된 `findByIdWithUser` 를 Query DSL로 변경합니다.
- 7번과 마찬가지로 N+1 문제가 발생하지 않도록 유의해 주세요!

우선 Query DSL을 사용하기 위해 build.gradle에 의존성을 추가해준다. 그리고 JpaConfig를 생성해주고, TodoRepositoryQuery와 TodoRepositoryQueryImpl을 생성해준다. TodoRepository는 TodoRepositoryQuery를 상속받는다. 그리고 TodoRepositoryQueryImpl에서 Query DSL로 findByIdWithUser를 구현한다.

### 9. Spring Security
- 기존 `Filter`와 `Argument Resolver`를 사용하던 코드들을 Spring Security로 변경해주세요.
    - 접근 권한 및 유저 권한 기능은 그대로 유지해주세요.
    - 권한은 Spring Security의 기능을 사용해주세요.
- 토큰 기반 인증 방식은 유지할 거예요. JWT는 그대로 사용해주세요.

우선 Spring Security를 사용하기 위해 build.gradle에 의존성을 추가해준다. 그리고 bcrypt는 Spring Security에 내장되어 있으므로 build.gradle에서 제거해준다.
AuthUserArgumentResolver와 JwtFilter는 각각 JwtAuthenticationToken와 JwtAuthenticationFilter가 대신 하므로 제거한다. 또 FilterConfig도 필요없어지므로 제거한다. 또 PasswordEncoder도 bcrypt와 같은 이유로 제거해준다. 대신 SecurityConfig를 생성해준다.
각 Controller에서는 이제 @Auth 대신 @AuthenticationPrincipal로 수정해준다. AuthUser Entity는 UserRole을 Collection<? extends GrantedAuthority> authorities로 수정해준다. 이는 Spring Security에서 권한이 여러 개 존재할 수도 있으므로 위와 같이 UserRole을 지원한다.
UserAdminController에서는 SecurityConfig에서 정의한 권한 정의를 changeUserRole에 @Secured(UserRole.Authority.ADMIN)를 붙여 명시적으로 ADMIN 권한을 가진 사용자만 사용 가능한 Method라고 알려준다. User Entity에서도 권한을 Collection으로 저장한다는 이유로 적절하게 수정한다. UserRole도 Spring Security가 지원하는 형태로 수정해준다.

## Level. 3
### 10. Query DSL을 사용하여 검색 기능 만들기
- 새로운 API로 만들어주세요.
- 검색 조건은 다음과 같아요.
    - 검색 키워드로 일정의 제목을 검색할 수 있어요.
        - 제목은 부분적으로 일치해도 검색이 가능해요.
    - 일정의 생성일 범위로 검색할 수 있어요.
        - 일정을 생성일 최신순으로 정렬해주세요.
    - 담당자의 닉네임으로도 검색이 가능해요.
        - 닉네임은 부분적으로 일치해도 검색이 가능해요.
- 다음의 내용을 포함해서 검색 결과를 반환해주세요.
    - 일정에 대한 모든 정보가 아닌, 제목만 넣어주세요.
    - 해당 일정의 담당자 수를 넣어주세요.
    - 해당 일정의 총 댓글 개수를 넣어주세요.
- 검색 결과는 페이징 처리되어 반환되도록 합니다.

TodoController에 getTodoWithCondition Method로 새로운 API를 만들어주고, RequestParam으로 page, size, title, nickname, startDate, endDate를 받아온다.
title, numberOfManagers, numberOfcomments를 반환해주는 TodoWithConditionResponse를 생성한다.
TodoService와 TodoRepositoryQuery에 각각 getTodoWithCondition과 findAllByCondition를 구현해주고, TodoRepositoryQueryImpl에서 Query DSL을 사용하여 title, numberOfManagers, numberOfcomments를 추출해준다.

### 11. Transaction 심화
- 매니저 등록 요청을 기록하는 로그 테이블을 만들어주세요.
    - DB 테이블명: `log`
- 매니저 등록과는 별개로 로그 테이블에는 항상 요청 로그가 남아야 해요.
    - 매니저 등록은 실패할 수 있지만, 로그는 반드시 저장되어야 합니다.
    - 로그 생성 시간은 반드시 필요합니다.
    - 그 외 로그에 들어가는 내용은 원하는 정보를 자유롭게 넣어주세요.

 id, userId, todoId, managerUserId, createdAt을 포함하는 Log Entity를 생성한다.
 매니저 등록과는 별개로 로그 테이블에 항상 요청 로그가 남을 수 있도록 @Transactional(propagation = Propagation.REQUIRES_NEW) 붙여주고, 현재 시간과 userId, todoId, managerUserId를 통해 Log Entity를 생성한다. 그리고 logRepository의 save를 통해 log를 저장한다.
ManagerService의 saveManager에서 saveManagerLogging을 호출한다.(유효하지 않은 요청까지 로그에 남길지 아니면 검증 로직들이 모두 지나고 Manager Entity가 생성되기 직전에 로그에 남길 지 고민)
