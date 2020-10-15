package sy.iyad.mikrotik;


import sy.iyad.mikrotik.PreReady.Task;

public interface OnCompleteListener<Tx> {
    void onComplete( Task<Tx> task);
}
