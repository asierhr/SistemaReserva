package com.asier.SistemaReservas.system.Locks;

import java.util.List;
import java.util.function.Supplier;

public interface DistributedLockService {
    <T> T executeWithMultiLock(List<String> lockKeys, Supplier<T> action);
}
