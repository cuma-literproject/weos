package com.clue.remote.types;


import com.clue.crypto.util.HexUtils;

public class EosNewAccount implements EosType.Packer {
    private TypeAccountName mCreator;
    private TypeAccountName mNewName;
    private TypeAuthority mOwner;
    private TypeAuthority mActive;
    private TypeAuthority mRecovery;
    private TypeAsset mDeposit;

    public String getTypeName() {
        return "newaccount";
    }

    public EosNewAccount(String creator, String newName,
                         TypeAuthority owner, TypeAuthority active, TypeAuthority recovery, TypeAsset deposit) {

        this( new TypeAccountName(creator), new TypeAccountName(newName), owner, active, recovery, deposit );
    }

    public EosNewAccount( String creator, String newName,
                          String ownerPubKeyInHex, String activePubKeyInHex, String recoveryAccountWithOneWeight) {

        this( new TypeAccountName(creator), new TypeAccountName(newName)
                , new TypeAuthority(1, ownerPubKeyInHex, null)
                , new TypeAuthority(1, activePubKeyInHex, null)
                , new TypeAuthority(1, null, recoveryAccountWithOneWeight)
                , new TypeAsset(1) );
    }

    public EosNewAccount(TypeAccountName creator, TypeAccountName newName,
                         TypeAuthority owner, TypeAuthority active, TypeAuthority recovery, TypeAsset deposit) {

        mCreator = creator;
        mNewName = newName;
        mOwner = owner;
        mActive = active;
        mRecovery = recovery;
        mDeposit = deposit;
    }

    public String getCreatorName(){
        return mCreator.toString();
    }

    @Override
    public void pack(EosType.Writer writer) {

        mCreator.pack(writer);
        mNewName.pack(writer);
        mOwner.pack(writer);
        mActive.pack(writer);
        mRecovery.pack(writer);
        mDeposit.pack(writer);
    }

    public String getAsHex() {
        EosType.Writer writer = new EosByteWriter(256);

        pack(writer);
        return HexUtils.toHex( writer.toBytes() );
    }
}
