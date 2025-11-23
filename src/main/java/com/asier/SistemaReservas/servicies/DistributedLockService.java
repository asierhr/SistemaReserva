package com.asier.SistemaReservas.servicies;

import java.util.List;
import java.util.function.Supplier;

public interface DistributedLockService {
    <T> T executeWithMultiLock(List<String> lockKeys, Supplier<T> action);
}
