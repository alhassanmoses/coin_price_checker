package com.misty.coinpricechecker;

class ValidAmountChecker {

    public static boolean isValidAmount(String amt){

        try {

            int temp = Integer.valueOf(amt);

            return true;

        }catch (NumberFormatException e){

            e.printStackTrace();
            return false;

        }

    }
}
