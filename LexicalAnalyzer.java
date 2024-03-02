
import java.util.ArrayList;

public class LexicalAnalyzer {

    private String[] reserved = {"AND", "ARRAY", "BEGIN", "CASE", "CONST", "DIV", "DO", "DOWNTO",
        "ELSE", "END", "FILE", "FOR", "FUNCTION", "GOTO", "IF", "IN",
        "LABEL", "MOD", "NIL", "NOT", "OF", "OR", "PACKED", "PROCEDURE",
        "PROGRAM", "RECORD", "REPEAT", "SET", "THEN", "TO", "TYPE",
        "UNTIL", "VAR", "WHILE", "WITH"};
    private String[] operator = {"+", "-", "*", "/", "=", "<>", "<", ">", "<=", ">=", ":=", ".", "(", ")", "(*", "*)", "{", "}", ":", ";", "\""};

    public boolean checkReserved(String input) {
        for (int i = 0; i <= reserved.length - 1; i++) {
            if (input.equals(reserved[i])) {
                return true;
            }
        }
        return false;
    }

    public boolean checkOperator(String input) {
        for (int i = 0; i <= operator.length - 1; i++) {
            if (input.equals(operator[i])) {
                return true;
            }
        }
        return false;
    }

    public boolean checkIdentifier(String input) {
        boolean checkIdent = input.matches("[a-zA-Z]+");
        return checkIdent;
    }

    public boolean checkNumber(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public boolean checkComment(ArrayList<String> input) {
        if (input.size() >= 8) {
            String start = input.get(1);
            String start2 = input.get(3);
            if (start.equals("(")) {
                if (start2.equals("*")) {
                    for (int i = 1; i <= input.size() - 1; i += 2) {
                        String current = input.get(i);
                        if (current.equals("*")) {
                            String current2 = input.get(i + 2);
                            if (current2.equals(")")) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean checkInitialization(ArrayList<String> input) {
        if (input.size() == 8) {
            if (input.get(0).equals("11")) {
                if (input.get(3).equals(":")) {
                    if (input.get(5).equals("INTEGER") || input.get(5).equals("REAL") || input.get(5).equals("CHAR") || input.get(5).equals("STRING") || input.get(5).equals("BOOLEAN")) {
                        if (input.get(7).equals(";")) {
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }

    public boolean checkAssignment(ArrayList<String> input) {
        if (input.size() >= 8) {
            if (input.get(0).equals("11")) {
                if (input.get(3).equals(":=")) {
                    if (input.get(4).equals("10") || input.get(4).equals("11") || input.get(4).equals("true") || input.get(4).equals("false")) {
                        //only assignment
                        if (input.size() == 8) {
                            if (input.get(7).equals(";")) {
                                return true;
                            }
                        }
                        //assignment + arithmetic
                        if (input.get(6).equals("24") || input.get(6).equals("21") || input.get(6).equals("22") || input.get(6).equals("23") || input.get(6).equals("%")) {
                            if (input.get(8).equals("10") || input.get(8).equals("11")) {
                                if (input.get(11).equals(";")) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean checkIf(ArrayList<String> input) {
        if (input.get(1).equals("IF")) {
            if (input.get(2).equals("25")) {
                if (input.get(4).equals("11")) {
                    if (input.get(7).equals("=") || input.get(7).equals(">") || input.get(7).equals("<") || input.get(7).equals("<=") || input.get(7).equals(">=")) {
                        if (input.get(8).equals("10") || input.get(8).equals("11")) {
                            if (input.get(10).equals("26")) {
                                if (input.get(13).equals("THEN")) {
                                    if (input.get(14).equals("11")) {
                                        if (input.get(16).equals("25")) {
                                            for (int i = 16; i <= input.size() - 1; i += 2) {
                                                if (input.get(i).equals("26")) {
                                                    if (input.size() >= i + 4) {
                                                        if (input.get(i + 3).equals("ELSE")) {

                                                            if (input.get(i + 4).equals("11")) {

                                                                if (input.get(i + 6).equals("25")) {
                                                                    for (int n = i + 6; i <= input.size() - 1; n += 2) {
                                                                        if (input.get(n).equals("26")) {

                                                                            if (input.get(n + 3).equals(";")) {
                                                                                return true;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else {
                                                        if (input.get(i + 3).equals(";")) {
                                                            return true;
                                                        }

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean checkBegin(ArrayList<String> input) {
        if (input.size() >= 2) {
            if (input.get(1).equals("BEGIN")) {
                return true;
            }
        }
        return false;
    }

    public boolean checkEnd(ArrayList<String> input) {
        if (input.size() >= 2) {
            if (input.get(1).equals("END.")) {

                return true;
            }
        }
        return false;
    }

    public boolean checkProgram(ArrayList<String> input) {
        if (input.size() == 6) {
            if (input.get(1).equals("PROGRAM")) {
                if (input.get(2).equals("11")) {
                    if (input.get(5).equals(";")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkVarInit(ArrayList<String> input) {
        if (input.size() == 2) {
            if (input.get(1).equals("VAR")) {
                return true;
            }
        }
        return false;
    }

}
