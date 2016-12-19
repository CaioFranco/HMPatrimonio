package br.senai.hm.util;

import java.util.ArrayList;

import br.senai.hm.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class AdapterListView extends BaseAdapter {
	
	private LayoutInflater mInflater;
	private ArrayList<ItemListView> itens;
	//private ArrayList<Patrimonio> patrimonios;
	
	public AdapterListView(Context context, ArrayList<ItemListView> itens) {
		//Itens que preencheram o listview
        this.itens = itens;
        //responsavel por pegar o Layout do item.
        mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		 return itens.size();
	}

	@Override
	public ItemListView getItem(int position) {
		 return itens.get(position);
	}

	@Override
	public long getItemId(int position) {
		  return position;
	}


	public View getView(int position, View view, ViewGroup parent) {
		//Pega o item de acordo com a posção.
        ItemListView item = itens.get(position);
        //infla o layout para podermos preencher os dados
        view =  mInflater.inflate(R.layout.item_listview, null);
        
        ((TextView) view.findViewById(R.id.id)).setText(item.getId());
        ((TextView) view.findViewById(R.id.modelo)).setText(item.getModelo());
        ((TextView) view.findViewById(R.id.descricao)).setText(item.getDescricao());
        ((CheckBox)  view.findViewById(R.id.checkBox1)).setTag(item.getCheck());;
        //((CheckBox) view.findViewById(R.id.checkBox1));

        //atravez do layout pego pelo LayoutInflater, pegamos cada id relacionado
        //ao item e definimos as informações.
		return view;
	}

}
