package com.example.service;

import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.service.Http.HttpCommunication;
import com.example.service.url.Defines;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class StudentHomeListAdapter extends RecyclerView.Adapter<StudentHomeListAdapter.MemberViewHolder> {

    private List<EventDetail> members;
    private Context context;
    String strUser="";


    JSONArray jarr = null;
    JSONObject json;
    String[] source = { "Pallavanthangal", "Tambaram", "Guindy"};

    String[] dest = { "CMBT", "Thiruvanmayur", "Tambaram"};

    String name,driver,phone,sourcetime,desttime,sourceplace,destplace,dates;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";


    public StudentHomeListAdapter(List<EventDetail> members, Context context) {
        this.members = members;
        this.context=context;
        //tempStorage = new TempStorage(context);

    }

    public class MemberViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        TextView Collegename,Dep,Eventname,Dis,Eventdate;
        private Button direction,confirm;

       // TempStorage tempStorage;

        Context context;
        TextView Lastreg,City;

        public MemberViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cv);
            Collegename = (TextView)itemView.findViewById(R.id.editname);
            Dep = (TextView) itemView.findViewById(R.id.editdriver);
            Eventname = (TextView) itemView.findViewById(R.id.editphone);
            Dis = (TextView) itemView.findViewById(R.id.etsourcetime);
            Eventdate=(TextView) itemView.findViewById(R.id.etdesttime);

            Lastreg=(TextView) itemView.findViewById(R.id.etsource);


            City=(TextView) itemView.findViewById(R.id.etdest);

            direction = (Button)itemView.findViewById(R.id.direction);

            confirm = (Button)itemView.findViewById(R.id.Confirm);




        }
    }


    @Override
    public void onBindViewHolder(MemberViewHolder memberViewHolder, final int i) {

        memberViewHolder.Collegename.setText("Shop Name : "+members.get(i).getCollegename());
       memberViewHolder.Dep.setText("Type of Service : "+members.get(i).getDepartment());
        memberViewHolder.Eventname.setText("owner Name : "+members.get(i).getEventname());

        memberViewHolder.Dis.setText("Offer : "+members.get(i).getDiscription());
        memberViewHolder.Eventdate.setText("Address : "+members.get(i).getEventdate());
        memberViewHolder.Lastreg.setText("Area : "+members.get(i).getLastreg());

        memberViewHolder.City.setText("City : "+members.get(i).getCity());
     //   memberViewHolder.etsource.setText(members.get(i).getPhone());



        final int pos=i;
        memberViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        memberViewHolder.direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Collegename=members.get(i).getCollegename();



                Intent intent=new Intent(context,EventLocation.class);
                intent.putExtra("Collegename",Collegename);

                context.startActivity(intent);
            }
        });

        memberViewHolder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Collegename=members.get(i).getCollegename();
                String Name = members.get(i).getName();
                String Email =members.get(i).getEmail();
                String event=members.get(i).getEventname();
                String day=members.get(i).getEventdate();

                //  Toast.makeText(context, "Series : "+series,Toast.LENGTH_LONG).show();


                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                params1.add(new BasicNameValuePair("Name", Name));
                params1.add(new BasicNameValuePair("Email", Email));
                params1.add(new BasicNameValuePair("Collegename", Collegename));

                params1.add(new BasicNameValuePair("Event", event));
                params1.add(new BasicNameValuePair("Day", day));


                json = HttpCommunication.makeHttpRequest(Defines.TAG_MAIL_SENT, "GET", params1, context);

                try {

                    if (json != null) {

                        //  Log.i("Jsonconvert",getPostDataString(json));

                        String message = json.getString(TAG_MESSAGE);
                        int success = json.getInt(TAG_SUCCESS);

                        if (success == 1) {
                           // Intent intent = new Intent(StudentHomeListAdapter.this, College_Event.class);

                            Toast.makeText(context, "You have registered Succesfull", Toast.LENGTH_LONG).show();
                            //  Intent i1 = new Intent(Student_Login.this, StudentHomeMaps.class);
                            // startActivity(i1);

                          //  startActivity(intent);

                        }
                        if (success == 0) {
                            Toast.makeText(context, "Not Registered", Toast.LENGTH_LONG).show();

                        }

                    }
                } catch (Exception e) {
                    Log.i("Error", e.getMessage());
                }
            }
        });


    }

    @Override
    public MemberViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.track_list_adapter, viewGroup, false);
        MemberViewHolder memberViewHolder = new MemberViewHolder(view);
        return memberViewHolder;
    }

    @Override
    public int getItemCount() {
        return members.size();
    }
}
