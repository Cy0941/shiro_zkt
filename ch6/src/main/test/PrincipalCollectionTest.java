import cn.cxy.shiro.entity.User;
import junit.framework.Assert;
import org.apache.shiro.subject.PrincipalCollection;
import org.junit.Test;

import java.util.Collection;
import java.util.Set;

/**
 * Function: TODO
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/6/16 15:39 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */
public class PrincipalCollectionTest extends BaseTest {

    @Test
    public void test() {

        //因为Realm里没有进行验证，所以相当于每个Realm都身份验证成功了
        login("shiro-multirealm.ini", "zhang", "123");
        //获取Primary Principal（即第一个）
        Object primaryPrincipal1 = subject.getPrincipal();
        PrincipalCollection principalCollection = subject.getPrincipals();
        Object primaryPrincipal2 = principalCollection.getPrimaryPrincipal();

        //但是因为多个Realm都返回了Principal，所以此处到底是哪个是不确定的
        Assert.assertEquals(primaryPrincipal1, primaryPrincipal2);


        //返回 a b c
        Set<String> realmNames = principalCollection.getRealmNames();
        System.out.println(realmNames);

        //因为MyRealm1和MyRealm2返回的凭据都是zhang，所以排重了
        Set<Object> principals = principalCollection.asSet(); //asList和asSet的结果一样
        System.err.println(principals);

        //根据Realm名字获取
        Collection<User> users = principalCollection.fromRealm("c");
        System.out.println(users);
    }

}
