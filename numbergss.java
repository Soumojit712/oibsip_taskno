import java.util.*;

public class numbergss {
    public  static void main(String [] args){
        int rdm = 0, num = 0 , at = 0 , fl = 0;
        Scanner sc = new Scanner(System.in);
        Random rd = new Random();
        num = rd.nextInt(100-1)+1;
        System.out.print("Rules:\n 1.Enter your guess number(1-100)\n 2.Score per round 10 if not attempted \n");
        do{
            System.out.println("Type number:\n"+(10-at)+" out of 10 attempts left");
            rdm= sc.nextInt();
            if(rdm>num){
                System.out.println("Too high! try to guess a smaller number this time." );
            }
            else if(rdm<num){
                System.out.println("Too low! try to guess a bigger number this time. " );
            }
            else{
                fl=1;
                System.out.println("Ok same number : " + num );
            }
            at++;
        }while((rdm!= num)&&(at<10));
        if(fl==0)
            System.out.println("The number was :"+num+"\nScore :");
        else{
            System.out.println("The score is : "+(100-at*10));
        }
        sc.close();
    }
}
