import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) {
        int unknown = 999;
        int int_lit = 10;
        int ident = 11;
        int reserve = 101;
        //OPERATORS
        int operator = 19;
        int assign_op = 20;
        int add_op = 21;
        int sub_op = 22;
        int mult_op = 23;
        int div_op = 24;
        int left_paren = 25;
        int right_paren = 26;
        boolean begin = false;
        boolean end = false;
        
        LexicalAnalyzer lex = new LexicalAnalyzer();
        try ( Scanner scanner = new Scanner(Paths.get("parse.txt"))) {
            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                StringTokenizer tokenizer = new StringTokenizer(row, "; ()\"", true);
                ArrayList<String> checkRow = new ArrayList<String>();
                while (tokenizer.hasMoreTokens()) {
                    String token = tokenizer.nextToken();
                    if (lex.checkReserved(token)) {
                        checkRow.add(String.valueOf(reserve));
                        checkRow.add(token);
                    } else if (lex.checkIdentifier(token)) {
                        checkRow.add(String.valueOf(ident));
                        checkRow.add(token);
                    } else if (lex.checkOperator(token)) {
                        switch (token) {
                            case ":=":
                                checkRow.add(String.valueOf(assign_op));
                                checkRow.add(token);
                                break;
                            case "+":
                                checkRow.add(String.valueOf(add_op));
                                checkRow.add(token);
                                break;
                            case "-":
                                checkRow.add(String.valueOf(sub_op));
                                checkRow.add(token);
                                break;
                            case "mult_op":
                                checkRow.add(String.valueOf(mult_op));
                                checkRow.add(token);
                                break;
                            case "div_op":
                                checkRow.add(String.valueOf(div_op));
                                checkRow.add(token);
                                break;
                            case "(":
                                checkRow.add(String.valueOf(left_paren));
                                checkRow.add(token);
                                break;
                            case ")":
                                checkRow.add(String.valueOf(right_paren));
                                checkRow.add(token);
                                break;
                            default:
                                checkRow.add(String.valueOf(operator));
                                checkRow.add(token);
                        }
                        
                    } else if (lex.checkNumber(token)) {
                        checkRow.add(String.valueOf(int_lit));
                        checkRow.add(token);
                    } 
                    else if(token.equals(" ")){
                        
                    }
                    else {

                        checkRow.add(String.valueOf(unknown));
                        checkRow.add(token);
                    }
                    
                }
                if(checkRow.isEmpty()){
                    //skip
                } 

                
                if(lex.checkProgram(checkRow)){
                    System.out.println("Syntax Accepted: Beginning program " + checkRow.get(3) + "\n");
                }
                else if(lex.checkBegin(checkRow)){
                    begin = true;
                    System.out.println("Syntax Accepted: Starting up\n");
                } else if(lex.checkVarInit(checkRow)){
                    System.out.println("Syntax Accepted: Variable initialization ahead\n");
                }
                else if(lex.checkComment(checkRow)){
                    System.out.println("Syntax Accepted: This is a comment\n");
                }
                else if(lex.checkInitialization(checkRow)){
                    System.out.println("Syntax Accepted: Initializing\n");
                } else if(lex.checkAssignment(checkRow)){
                    System.out.println("Syntax Accepted: Assigning\n");
                } else if(lex.checkEnd(checkRow)){
                    end = true;
                    if(end == true && begin == true){
                        System.out.println("Syntax Accepted: Ending Program\n");
                    } else{
                        System.out.println("Syntax Error: There is no beginning\n");
                    }
                } else if(checkRow.isEmpty()){
                    //skip
                } else if(lex.checkIf(checkRow)){
                    System.out.println("Syntax Accepted: This is an if statement\n");
                }
                else{
                    System.out.println("Syntax Error\n");
                }
                
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        

    } 
    

}
