package hello.core.singleton;

public class SingletonService {

    private static final SingletonService instance = new SingletonService();

    // 외부에서 요청 시 초기화해둔 동일한 인스턴스 반환
    public static SingletonService getInstance() {
        return instance;
    }

    // 외부에서 생성하지 못하도록 막음
    private SingletonService() {}

    public void logic() {
        System.out.println("싱글톤 서비스를 호출하였습니다.");
    }

}
