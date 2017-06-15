/**
 * Function: TODO
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/6/15 12:02 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */
public class MainTest {

    public static void main(String[] args){
        String s = "+资源字符串+权限位+实例ID";
        String[] split = s.split("\\+");
        for (String s1 : split) {
            System.out.println(s1);
        }
    }

}
