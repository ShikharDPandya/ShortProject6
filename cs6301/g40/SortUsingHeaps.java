package cs6301.g40;

import java.util.*;

/*
 * Group members:
Mukesh Kumar(mxk170430)
Shikhar Pandya (sdp170030)
Arijeet Roy (axr165030)*/
public class SortUsingHeaps {
    int k;
    ArrayList<Integer> num;
    ArrayList<ArrayOf1Fragment> KFrags;

    public SortUsingHeaps(int numFrags, ArrayList<Integer> arr)
    {
        this.k=numFrags;
        this.num = arr;
        this.KFrags = new ArrayList<>();
    }

    /*
    mergesort to sort individual arrays
     */
    public static ArrayList<Integer> mergeSort(ArrayList<Integer> num)
    {
        if(num.size() < 5)
        {
            Collections.sort(num);
            return num;
        }
        else
        {
            ArrayList<Integer> firstHalfElem = new ArrayList<>(num.subList(0,num.size()/2));
            ArrayList<Integer> secondHalfElem = new ArrayList<>(num.subList(num.size()/2,num.size()));
            ArrayList<Integer> temp1 = new ArrayList<>();
            ArrayList<Integer> temp2 = new ArrayList<>();

            temp1 = mergeSort(firstHalfElem);
            temp2 = mergeSort(secondHalfElem);
            return merge(temp1,temp2);
        }
    }

    /*
    Only used to merge single array. Helper function for MergeSort function
     */

    public static ArrayList<Integer> merge(ArrayList<Integer> list1, ArrayList<Integer> list2)
    {
        ArrayList<Integer> merged = new ArrayList<>();
        int indexList1 = 0,indexList2 = 0;
        while(indexList1 < list1.size() && indexList2 < list2.size())
        {
            if(list1.get(indexList1) >= list2.get(indexList2) )
            {
                merged.add(list2.get(indexList2));
                indexList2++;
            }
            else
            {
                merged.add(list1.get(indexList1));
                indexList1++;
            }
        }
        while (indexList1 < list1.size())
        {
            merged.add(list1.get(indexList1));
            indexList1++;
        }

        while (indexList2 < list2.size())
        {
            merged.add(list2.get(indexList2));
            indexList2++;
        }
        return merged;
    }

    /*
    Uses Priority queue to merge K arrays
     */

    public static ArrayList<Integer> KwayMerge(ArrayList<ArrayOf1Fragment> listOfList)
    {
        ArrayOf1Fragment.LinkNumberToArrayNumber minNum = new ArrayOf1Fragment.LinkNumberToArrayNumber(0,0);
        int[] kCounters = new int[listOfList.size()];
        Arrays.fill(kCounters,0);
        ArrayList<Integer> merged = new ArrayList<>();
        PriorityQueue<ArrayOf1Fragment.LinkNumberToArrayNumber> pq = new PriorityQueue<>();

        //for loop creates the initial priority queue by adding the first elements of each array
        for (int i = 0; i < listOfList.size(); i++)
        {
            pq.add(listOfList.get(i).l.get(0));
        }
        //loop till the priority queue is not empty
        while (!pq.isEmpty())
        {
            //perform extractMin from priority queue and increment the index counter corresponding to its array
            minNum=pq.remove();
            kCounters[minNum.arrayIdentifier]++;

            //check to prevent array out of bounds exception
            if(kCounters[minNum.arrayIdentifier] < listOfList.get(minNum.arrayIdentifier).l.size())
            {
                pq.add(listOfList.get(minNum.arrayIdentifier).l.get(kCounters[minNum.arrayIdentifier]));
            }
            merged.add(minNum.number);
        }
        return merged;
    }

    /*
    Takes a single ArrayList as input and divides it into K parts. If total number of elements is not divisible by k,
    the extra elements are put into the 1st ArrayList
     */

    public static ArrayList<ArrayList<Integer>> returnKFragmentedList(ArrayList<ArrayList<Integer>> listOfList, ArrayList<Integer> list, int k)
    {
        int sizeOfOneList = list.size()/k;
        int counter=0;
        while(counter <= (k-1))
        {
            ArrayList<Integer> l = new ArrayList<Integer>(list.subList(counter * sizeOfOneList, (counter + 1)* sizeOfOneList));
            listOfList.add(l);
            counter++;
        }
        if(list.size()%k != 0)
        {
            int remainingElems = list.size()-(k * sizeOfOneList);
            while(remainingElems != 0)
            {
                listOfList.get(0).add(list.get(remainingElems + (k * sizeOfOneList) - 1)); // -1 to make sure index is not out of bound
                remainingElems--;
            }
        }
        return listOfList;
    }

    /*
    takes in multiple sorted ArrayLists (listOfList) and convert it into ArrayList of objects of 'ArrayOf1Fragment'.
    Array1OfFragment keeps the mapping of each element of the array and its corresponding array number.
     */
    public static ArrayList<ArrayOf1Fragment> convertDataStructure(ArrayList<ArrayList<Integer>> listOfList, ArrayList<ArrayOf1Fragment> Klists)
    {
        int index = 0;
        for (ArrayList<Integer> l1:listOfList)
        {
            ArrayOf1Fragment arr1 = new ArrayOf1Fragment(l1,index);
            Klists.add(arr1);
            index++;
        }
        return Klists;
    }

    /*public static void print(ArrayList<Integer> l)
    {
        System.out.println("Sorted Array of size: " + l.size()+" " +l);
    }
        */
    public static void main(String[] args)
    {
        int numFrags = 0,num=0;
        ArrayList<Integer> arrayOfNums = new ArrayList<>();
        ArrayList<ArrayOf1Fragment> KListswithNewDataStructure = new ArrayList<>();
        ArrayList<ArrayList<Integer>> KLists = new ArrayList<>();
        ArrayList<ArrayList<Integer>> KSortedLists = new ArrayList<>();
        Scanner in = new Scanner(System.in);

        System.out.println("Enter value of k");
        numFrags = in.nextInt();

        /*
        Generate 50 random numbers for input array
         */
        Random rand = new Random();
        for(long i = 1; i <= 50; i++)
        {
            num = rand.nextInt(100);
            arrayOfNums.add(num);
        }

        System.out.println("Array of size " + arrayOfNums.size()+ " " + arrayOfNums);

        /*
            divide array into multiple arrays depending on number of fragments (the k in k way merge)
         */
        KLists = returnKFragmentedList(KLists,arrayOfNums,numFrags);

        /*
        Sort individual arrays
         */
        for (ArrayList<Integer> l: KLists)
        {
            KSortedLists.add(mergeSort(l));
        }

        /*
            converts multiple sorted Array lists into appropriate data structure which can be used for K way merging
         */
        KListswithNewDataStructure = convertDataStructure(KSortedLists,KListswithNewDataStructure);

        /*
        K way merge the multiple sorted Array List and return as single arrayList
         */
        arrayOfNums = KwayMerge(KListswithNewDataStructure);

        System.out.println("Sorted Array of size: " + arrayOfNums.size()+" " +arrayOfNums);

    }
}
