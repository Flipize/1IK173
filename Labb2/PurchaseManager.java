package Labb2;

import java.util.Date;

public class PurchaseManager implements IPurchaseManager{

    PurchaseStore store=null;

    public PurchaseManager(PurchaseStore ps) { // kopplingen mellan PurchaseManager och PurchaseStore
        store=ps;
    }

    //@Override
    public float sumOfMonth(int year, int month) {
        Date date1 = new Date(year, month, 1);
        Date date2 = new Date(year,month,31);

        Purchase[] purchases = store.getPurchases(date1,date2);
        float summa=0;

        for (int i=0; i<purchases.length;i++) { // itererar alla datumen och lägger ihop köpen för just den månaden
            if (purchases[i].getDate().compareTo(date1)>=0 && purchases[i].getDate().compareTo(date2) <=0) {
                summa += purchases[i].getAmount();
            }
        }
        return summa;
    }

    //@Override
    public float [] monthlyAverage(int year) {
        Date date1 = new Date(year,1,1);
        Date date2 = new Date(year,11,31);
        Purchase[] purchases = store.getPurchases(date1,date2);
        final float[] avg = new float[12];
        int counter = 0; // för att räkna samtliga månaderna

        for (int i=0; i < avg.length; i++) { // itererar 12 månader
            for (int j=0; j<purchases.length; j++) { // itererar samtliga köp
                if (purchases[j].getDate().compareTo(date1)>= 0 && purchases[j].getDate().compareTo(date2)<= 0 && purchases[j].getDate().getMonth()==i){
                    avg[i] = avg[i] + purchases[j].getAmount();
                    counter++;
                }
            }
            if (counter==0) {
                avg[i] = 0.0f; // f = "float"
            } else {
                avg[i] = avg[i] / counter; // summan delat med 12
                counter = 0; //ställer om countter till noll.
            }
        }
        for (int i=0; i < avg.length; i++) {
            System.out.print(avg[i] + " ");
        }
        return avg;
    }

    //@Override
    public float[] yearlyAveragePerCategory(int year) {
        Date date1 = new Date(year,0,1);
        Date date2 = new Date(year,11,31);

        Purchase[] purchases = store.getPurchases(date1,date2);
        Category[] categories = store.getAllCategories();

        float [] avg = new float[categories.length];
        int counter = 0;
        int index = 0;

        for (int i = 0; i < categories.length; i++) {
            for (int j = 0; j < purchases.length; j++) {
                if (purchases[j].getDate().compareTo(date1)>= 0 && purchases[j].getDate().compareTo(date2) <= 0 && purchases[j].getCategoryId() == categories[i].getID()){
                    avg[index] += purchases[j].getAmount();
                    counter++;
                }
            }
            avg[index] = avg[index]/counter; // för att få fram medelvärde
            index++;
            counter = 0;
        }

        for (int i = 0; i<avg.length; i++) {
            System.out.print(avg[i]+" ");
        }
        return avg;
    }
}
