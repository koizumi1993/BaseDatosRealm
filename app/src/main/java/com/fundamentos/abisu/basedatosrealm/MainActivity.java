package com.fundamentos.abisu.basedatosrealm;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fundamentos.abisu.basedatosrealm.Adaptadores.WaifuAdaptador;
import com.fundamentos.abisu.basedatosrealm.Modelos.WaifuModelo;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements RealmChangeListener<RealmResults<WaifuModelo>> {
    public Realm realm;

    public FloatingActionButton floatingActionButton;

    private RealmResults<WaifuModelo> listaWaifus;
    private RecyclerView recyclerViewWaifu;
    private WaifuAdaptador waifuAdapatador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        floatingActionButton = findViewById(R.id.fabNuevoCategoria);

        listaWaifus = realm.where(WaifuModelo.class).findAll();
        listaWaifus.addChangeListener(this);
        recyclerViewWaifu = findViewById(R.id.recyclerCategoria);
        recyclerViewWaifu.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        waifuAdapatador = new WaifuAdaptador(getApplicationContext(), listaWaifus);
        recyclerViewWaifu.setAdapter(waifuAdapatador);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertNuevoLibro();
            }
        });

        waifuAdapatador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WaifuModelo waifuModelo = listaWaifus.get(recyclerViewWaifu.getChildAdapterPosition(v));
                alertEditarLibro(waifuModelo);
            }
        });

        waifuAdapatador.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                WaifuModelo waifuModelo = listaWaifus.get(recyclerViewWaifu.getChildAdapterPosition(v));
                realm.beginTransaction();
                assert waifuModelo != null;
                waifuModelo.deleteFromRealm();
                realm.commitTransaction();
                return false;
            }
        });
    }

    private void alertEditarLibro(final WaifuModelo waifuModelo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialogo_nueva_waifu,null);
        builder.setView(view);

        final EditText editTextNombre = view.findViewById(R.id.edtNombre);
        final EditText editTextDesc = view.findViewById(R.id.edtDescripcion);
        final EditText editTextImage = view.findViewById(R.id.edtImagen);
        builder.setMessage("Update waifu");

        editTextImage.setText(waifuModelo.getImagen());
        editTextNombre.setText(waifuModelo.getNombre());
        editTextDesc.setText(waifuModelo.getDescripcion());

        editTextNombre.setSelection(editTextNombre.getText().length());//posicionamos el cursor al final

        builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nombre = editTextNombre.getText().toString().trim();
                String descricion = editTextDesc.getText().toString().trim();
                String imagen = editTextImage.getText().toString().trim();
                if (nombre.length() > 0){
                    realm.beginTransaction();
                    waifuModelo.setNombre(nombre);
                    waifuModelo.setDescripcion(descricion);
                    waifuModelo.setImagen(imagen);
                    realm.commitTransaction();
                }else {
                    Toast.makeText(getApplicationContext(),"No se pueden actualizar los campos",Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void alertNuevoLibro() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialogo_nueva_waifu,null);
        builder.setView(view);

        final EditText editTextNombre = view.findViewById(R.id.edtNombre);
        final EditText editTextDesc = view.findViewById(R.id.edtDescripcion);
        final EditText editTextImage = view.findViewById(R.id.edtImagen);
        builder.setMessage("Add new waifu");
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nombre = editTextNombre.getText().toString().trim();
                String descricion = editTextDesc.getText().toString().trim();
                String imagen = editTextImage.getText().toString().trim();
                if (nombre.length()>0){
                    realm.beginTransaction();
                    WaifuModelo waifuModelo = new WaifuModelo(nombre,imagen,descricion);
                    realm.copyToRealm(waifuModelo);
                    realm.commitTransaction();
                }else {
                    Toast.makeText(getApplicationContext(),"Llene todos los campos por favor",Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onChange(RealmResults<WaifuModelo> waifuModelos) {
        waifuAdapatador.notifyDataSetChanged();
    }
}
