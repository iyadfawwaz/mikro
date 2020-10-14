package sy.iyad.mikrotik;

import androidx.annotation.NonNull;

import sy.iyad.mikrotik.PreReady.Task;

public interface OnCompleteListener<Tx> {
    void onComplete(@NonNull Task<Tx> task);
}
