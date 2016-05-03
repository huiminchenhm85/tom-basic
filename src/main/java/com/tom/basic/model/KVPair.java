package com.tom.basic.model;

public class KVPair<K,V> {
	
	public KVPair(){}
	
	public KVPair(K k,V v){
		this.key = k;
		this.value = v;
	}
	
	public K key;
	
	public V value;

}
