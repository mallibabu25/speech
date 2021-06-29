package com.speech;

import java.util.Locale;

public class ScoreCalculator {
    private static String[] b = { "\"", "?", ".", ",", "'", "!" };
    private static String[] c = { ":", "/", "   ", "  " };

    public static double b(String paramString1, String paramString2)
    {
        if ((paramString2 != null) && (paramString1 != null)) {
            return c(b(paramString1), b(paramString2));
        }
        return 0.0D;
    }


    private static double c(String paramString1, String paramString2)
    {
        if (paramString1 != null)
        {
            if (paramString2 == null) {
                return 0.0D;
            }
            if (paramString1.equals(paramString2)) {
                return 1.0D;
            }
            String str2 = paramString1;
            String str1 = paramString2;
            if (paramString1.length() < paramString2.length())
            {
                str1 = paramString1;
                str2 = paramString2;
            }
// d1 = met = 3

            double d1 = str2.length();
            if (d1 == 0.0D) {
                return 1.0D;
            }
// str1 = mat str2 = met
//            meat meet
//            meat mat
//            mat meat

//d2= 1

            double d2 = d(str2, str1);
            Double.isNaN(d1);
            Double.isNaN(d2);
            Double.isNaN(d1);
            return (d1 - d2) / d1;
// 3-1/3 * 100


        }
        return 0.0D;
    }


    public static String b(String paramString)
    {
        String[] arrayOfString = c;
        int i1 = arrayOfString.length;
        int n = 0;
        int m = 0;
        while (m < i1)
        {
            paramString = paramString.replace(arrayOfString[m], " ");
            m += 1;
        }
        arrayOfString = b;
        i1 = arrayOfString.length;
        m = 0;
        while (m < i1)
        {
            paramString = paramString.replace(arrayOfString[m], "");
            m += 1;
        }
        arrayOfString = new String[2];
        arrayOfString[0] = "   ";
        arrayOfString[1] = "  ";
        i1 = arrayOfString.length;
        m = n;
        while (m < i1)
        {
            paramString = paramString.replace(arrayOfString[m], " ");
            m += 1;
        }
        return paramString.toUpperCase(Locale.ENGLISH).trim();
    }


    private static int d(String paramString1, String paramString2)
    {
        paramString1 = paramString1.toUpperCase(Locale.ENGLISH);
        paramString2 = paramString2.toUpperCase(Locale.ENGLISH);
        int[] arrayOfInt = new int[paramString2.length() + 1];
        int m = 0;
        while (m <= paramString1.length())
        {
            int i2 = m;
            int i1 = 0;
            while (i1 <= paramString2.length())
            {
                int n;
                if (m == 0)
                {
                    arrayOfInt[i1] = i1;
                    n = i2;
                }
                else
                {
                    n = i2;
                    if (i1 > 0)
                    {
                        int i4 = i1 - 1;
                        int i3 = arrayOfInt[i4];
                        n = i3;
                        if (paramString1.charAt(m - 1) != paramString2.charAt(i4)) {
                            n = Math.min(Math.min(i3, i2), arrayOfInt[i1]) + 1;
                        }
                        arrayOfInt[i4] = i2;
                    }
                }
                i1 += 1;
                i2 = n;
            }
            if (m > 0) {
                arrayOfInt[paramString2.length()] = i2;
            }
            m += 1;
        }
        return arrayOfInt[paramString2.length()];
    }
}
