/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * package com.example.android.justjava;
 */
package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */

public class MainActivity extends AppCompatActivity {

    int quantity=1;
    private int name_view;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if(quantity==100){
            Toast.makeText(this, "Você não pode solicitar mais do que 100 copos de café", Toast
                    .LENGTH_SHORT).show();
            return;
        }
        display(++quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if(quantity==1){
            Toast.makeText(this, "Você não pode solicitar menos do que 1 copo de café", Toast.LENGTH_SHORT).show();
            return;
        }
        display(--quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

            boolean isCheckedCream = ((CheckBox) findViewById(R.id.whipped_cream_checkbox)).isChecked();
            boolean isCheckedChocolate = ((CheckBox) findViewById(R.id.chocolate_checkbox))
                    .isChecked();
            String name = ((EditText) findViewById(R.id.name_field)).getText().toString();

            int price = calculatePrice(5,isCheckedCream,isCheckedChocolate);
            String orderSummaryMessage = createOrderSummary(price,name,isCheckedCream,isCheckedChocolate);
            //displayMessage(orderSummaryMessage);

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_TEXT, orderSummaryMessage);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Order for "+name);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
    }

    /**
     * This method create a order summary.
     * @param price of the order.
     * @return text summary.
     */
    public String createOrderSummary(int price, String name, boolean isCheckedCream, boolean
            isCheckedChocolate) {
        String orderSummaryText = getString(R.string.order_summary_name,name)+"\n"+
                getString(R.string.add)+" "+getString(R.string.whipped_cream)+"? "
                +isCheckedCream+"\n"+
                getString(R.string.add)+" "+getString(R.string.chocolate)+"? " +isCheckedChocolate+"\n" +
                getString(R.string.quantity)+": "+quantity+"\nTotal: " +
                NumberFormat.getCurrencyInstance().format(price)+
                "\n"+getString(R.string.thank_you);
        return orderSummaryText;
    }

    /**
     * Calculates the price of the order.
     *
     * @param pricePerCup is the price per cup of coffee.
     * @return total price of the order.
     */
    private int calculatePrice( int pricePerCup, boolean isCheckedCream, boolean
            isCheckedChocolate) {
        int price=0;
        if (isCheckedCream)
            price=price+1;
        if(isCheckedChocolate)
            price=price+2;
        price=price+pricePerCup;
        price = quantity * price;
        return price;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

}