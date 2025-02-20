// CARLOS ROMEO CLEMENTE DEL CASTILLO III
// UNIVERSITY OF THE CORDILLERAS, BSIT-1L
// 31OP/Object-Oriented Programming
// [31OP_00AL] Pretest Activity: Algorithms
// 15 Jan. 2025 W


public class carodc3_31OP_00AL {

    static int realValueOfDigit(char c) {
        int res = switch(c) {
            case '0' -> 0;
            case '1' -> 1;
            case '2' -> 2;
            case '3' -> 3;
            case '4' -> 4;
            case '5' -> 5;
            case '6' -> 6;
            case '7' -> 7;
            case '8' -> 8;
            case '9' -> 9;
            case 'A', 'a' -> 10;
            case 'B', 'b' -> 11;
            case 'C', 'c' -> 12;
            case 'D', 'd' -> 13;
            case 'E', 'e' -> 14;
            case 'F', 'f' -> 15;
            default -> -1;
        };
        return res;
    }

    public static void main(String[] args) {
        System.out.println(realValueOfDigit('a'));
    }
}
