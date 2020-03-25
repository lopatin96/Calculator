package re.shpo.calculator;

import android.os.AsyncTask;


interface AsyncResponse {
    void processFinish(Double output);
    void processUpdate(Integer output);
}

public class PiComputeTask extends AsyncTask<Void, Integer, Double> {


    public AsyncResponse delegate = null;

    protected Double doInBackground(Void... voids) {
        int n = 10000;
        double x, y;
        int k = 0;
        for (int i = 0; i <= n; i++) {
            x = Math.random();
            y = Math.random();
            if (x * x + y * y <= 1) k++;
        }
        return 4. * k / n;
    }

    protected void onPostExecute(Double result) {
        delegate.processFinish(result);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        System.out.println("UPDATE" + values.toString());
    }
}
