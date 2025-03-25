package hello.core.singleton;

public class SingletoneService {

    private static final SingletoneService instance = new SingletoneService();

    public static SingletoneService getInstance() {
        return instance;
    }
    
    //생성자를 private 으로 만들어서 외부에서 객체 생성하면 에러나도록 만들어버림
    private SingletoneService() {
    }
    
    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
