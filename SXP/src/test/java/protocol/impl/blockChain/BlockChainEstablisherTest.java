package protocol.impl.blockChain;

import controller.Application;
import crypt.api.hashs.Hasher;
import crypt.factories.EthereumKeyFactory;
import crypt.factories.HasherFactory;
import model.api.SyncManager;
import model.entity.ContractEntity;
import model.entity.EthereumKey;
import model.entity.User;
import model.syncManager.UserSyncManagerImpl;

import org.junit.AfterClass;
import org.junit.Test;

import protocol.impl.BlockChainEstablisher;
import util.TestInputGenerator;
import util.TestUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class BlockChainEstablisherTest {

    public static Application application;
    public static final int restPort = 8081;

    public static final int N = 2;

    private ContractEntity[] contractEntity = new ContractEntity[N];
    private BlockChainContract bcContractA, bcContractB;

    @SuppressWarnings("unused")
	private ArrayList<String> setEntityContract(String... entity) {
        ArrayList<String> newEntities = new ArrayList<>();
        for (String tmp : entity) {
            newEntities.add(tmp);
        }
        return newEntities;
    }

    @AfterClass
    static public void deleteBaseAndPeer() {
        TestUtils.removeRecursively(new File(".db-" + restPort + "/"));
        TestUtils.removePeerCache();
        application.stop();
    }


    @Test
    public void Test() throws Exception {
    	
        if (Application.getInstance() == null) {
            application = new Application();
            Application.getInstance().runForTests(restPort);
        }

        //Add Ethereum Account
        EthereumKey keys0 = EthereumKeyFactory.createFromKeystore(
        		"C:\\Users\\cha\\AppData\\Roaming\\Ethereum\\devnet\\keystore\\UTC--2017-11-29T06-32-47.458216900Z--e3555423064d46624b86204a1810dbf8b22fba40",
        		"");
        EthereumKey keys1 = EthereumKeyFactory.createFromKeystore(
        		"C:\\Users\\cha\\AppData\\Roaming\\Ethereum\\devnet\\keystore\\UTC--2017-11-29T06-33-33.152800100Z--01b2bb58a6478a2c90fab8c32621543c9de5ab61",
        		"test");
        /*EthereumKey keys0 = EthereumKeyFactory.createNew();
        EthereumKey keys1 = EthereumKeyFactory.createNew();*/
        
        System.out.println(keys0.getAddress());
        System.out.println(keys0.getStringPublicKey());
        System.out.println(keys0.getStringPrivateKey());
        
        System.out.println();
        
        System.out.println(keys1.getAddress());
        System.out.println(keys1.getStringPublicKey());
        System.out.println(keys1.getStringPrivateKey());

        // Creating the users
        User[] users = new User[N];
        ArrayList<String> parties = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            String login  = TestInputGenerator.getRandomAlphaWord(20);
            String password = TestInputGenerator.getRandomPwd(20);

            users[i] = new User();
            users[i].setNick(login);
            Hasher hasher = HasherFactory.createDefaultHasher();
            users[i].setSalt(HasherFactory.generateSalt());
            hasher.setSalt(users[i].getSalt());
            users[i].setPasswordHash(hasher.getHash(password.getBytes()));
            users[i].setCreatedAt(new Date());

            //Attribute EthereumKey
            if (i == 0) {
                users[i].setEthereumKey(keys0);
            } else {
                users[i].setEthereumKey(keys1);
            }

            SyncManager<User> em = new UserSyncManagerImpl();
            em.begin();
            em.persist(users[i]);
            em.end();

            parties.add(users[i].getId());
        }

        ///////////////////////////////
        //Add Entities in Contracts Entity
        for (int i = 0; i < N; i++) {
            contractEntity[i] = new ContractEntity();
            contractEntity[i].setParties(parties);
            System.out.println("USERS : " + contractEntity[i].getParties().toString());

            ArrayList<String> clauses = new ArrayList<>();
            clauses.add(users[0].getId() + " troc item1 with " + users[1].getId());
            clauses.add(users[1].getId() + " troc item2 with " + users[0].getId());
            contractEntity[i].setClauses(clauses);

            contractEntity[i].setCreatedAt(new Date());
        }
        //End Add Entities
        ///////////////////////////////

        ArrayList<EthereumKey> partis = new ArrayList<>();
        partis.add(users[0].getEthereumKey());
        partis.add(users[1].getEthereumKey());

        // Map of URIS
        HashMap<EthereumKey, String> uris = new HashMap<>();
        String uri = Application.getInstance().getPeer().getUri();

        for (int k = 0; k < N; k++) {
            EthereumKey key = new EthereumKey();
            key.setPublicKey(users[k].getEthereumKey().getPublicKey());
            uris.put(key, uri);
        }

        bcContractA = new BlockChainContract(contractEntity[0], partis);
        bcContractB = new BlockChainContract(contractEntity[1], partis);


        BlockChainEstablisher bcEstablisherA = new BlockChainEstablisher(users[0], uris, "127.0.0.1", "8545");
        BlockChainEstablisher bcEstablisherB = new BlockChainEstablisher(users[1], uris, "127.0.0.1", "8545");

        bcEstablisherA.initialize(bcContractA, true);

        sleep(2000);

        bcEstablisherB.initialize(bcContractB, false);

        sleep(2000);

        bcEstablisherA.start();

        //Time to sendContractAddr and set it
        sleep(2000);

        bcEstablisherA.sign(bcContractB);

        sleep(30000);

        bcEstablisherB.sign(bcContractA);

        //time to EstablisherA check if finalized when EstablisherB share Tx Signature
        sleep(300000);

        System.out.println("\n\n[Entity<A> Final State] :");
        for (String sign : contractEntity[0].getSignatures().keySet()) {
            System.out.println("\t" + sign);
        }

        System.out.println("\n\n[Entity<B> Final State] :");
        for (String sign : contractEntity[1].getSignatures().keySet()) {
            System.out.println("\t" + sign);
        }
    }

    public void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
