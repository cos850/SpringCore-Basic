package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;

public class OrderServiceImpl implements OrderService {

    /**
     * 할인 정책을 FixDiscountPolicy에서 RateDiscountPolicy로 변경이 필요한 상황.
     * 그러나 현재 코드가 추상 인터페이스에만 의존하지 않고, 구체 클래스에도 함께 의존하기 때문에 클라이언트코드의 수정이 일어나게 된다!! (OCP, DIP 위반)
     * 이를 해결하려면 관심사 분리가 필요하다.
     * => 책임을 분리해서 클라이언트는 추상에만 의존하도록 하고, 구체 클래스 할당을 관리해주는 책임을 가진 다른 클래스가 있어야 한다.
     */
//    private final MemberRepository memberRepository = new MemoryMemberRepository();
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy= new RateDiscountPolicy();

    /**
     * 생성자를 통해 외부에서 구체 클래스를 받게 되었다. (생성자 의존관게 주입, DIP가 잘 지켜짐)
     * 현재 코드를 더이상 고칠 필요 없이 추상 인터페이스에만 의존하기 때문에,
     * 할인 정책 또는 사용자 정보 저장 방법이 변경되어도 코드를 수정하지 않아도 된다.
     * 이제 더이상 클라이언트는 구체 클래스에 대해 알 수 없다!
     */
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
        this.memberRepository = memberRepository;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
