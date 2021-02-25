package utils;

public interface OnInputListener {
   void inputNumber(int number);
   void inputLetter(String str, boolean isUpperCase);
   void IsDeleteClick(boolean isClick);
   void isBackClick(boolean isClick);
}
