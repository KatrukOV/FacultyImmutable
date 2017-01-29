package com.katruk.dao.cache;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public interface Cache<K, V> {

  V getValue(K key, Callable<V> callable)
      throws InterruptedException, ExecutionException;

  void setValueIfAbsent(K key, V value);
}
