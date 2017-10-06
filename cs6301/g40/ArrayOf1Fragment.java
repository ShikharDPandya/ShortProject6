package cs6301.g40;

import java.util.ArrayList;

public class ArrayOf1Fragment {

    ArrayList<LinkNumberToArrayNumber> l;

    public static class LinkNumberToArrayNumber implements Comparable<LinkNumberToArrayNumber>
    {
        int number;
        int arrayIdentifier;

        public LinkNumberToArrayNumber(int num, int whichArray)
        {
            this.number = num;
            this.arrayIdentifier = whichArray;
        }

        public int compareTo(LinkNumberToArrayNumber other)
        {
            if(this.number > other.number)
                return 1;
            else if(this.number < other.number)
                return -1;
            else
                return 0;
        }
    }
    public ArrayOf1Fragment(ArrayList<LinkNumberToArrayNumber> obj)
    {
        this.l = obj;
    }

    public ArrayOf1Fragment(ArrayList<Integer> intArr, int arrayNum)
    {
        this.l = new ArrayList<>();
        for (Integer i:intArr)
        {
            LinkNumberToArrayNumber numAndArrayNum1 = new LinkNumberToArrayNumber(i,arrayNum);
            l.add(numAndArrayNum1);
        }
    }
}
