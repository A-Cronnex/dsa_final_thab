package DataStructures;

public class MyHashMap {
    protected double lambdaFactor = 0.7;
    protected int num_elements = 10;
    protected int numBuckets = (int) (num_elements/lambdaFactor);
    protected int realNumBuckets = getNextPrime();
    protected Bucket[] array = new Bucket[this.realNumBuckets];
    public MyHashMap(){

        for (int i = 0; i< realNumBuckets; i++){
            array[i] = new Bucket();
        }
    }

    protected Integer hash(String key){
        int hash = 0;

        for (int i = 0; i < key.length(); i++){
            hash = this.realNumBuckets * key.toCharArray()[i] + hash;
        }
        return hash;
    }

    protected Integer hashFunction(String key){
        return hash(key) % this.realNumBuckets;
    }

    private Integer getNextPrime (){

        boolean isPrimevalBuckets = isPrime(2);

        while (!isPrimevalBuckets){
            this.numBuckets++;
            isPrimevalBuckets = isPrime(2);

        }

        return this.numBuckets;
    }

    private boolean isPrime(int index) {

        if (index < this.numBuckets){
            if (this.numBuckets % index == 0) {
                return false;
            } else {
                return isPrime(index + 1);
            }
        }

        return true;
    }

    public String searchValue(String key){
        BucketNode<String,String> node = searchNode((key));

        if (node != null){
            return node.getValue();
        }

        return null;

    }

    private BucketNode<String,String> searchNode(String key){
        int hashValue =hashFunction(key);
        Bucket bucket = array[hashValue];

        return bucket.findValueRecursive(bucket.getHead(),key);

    }

    public void insert(String key, String value){
        BucketNode<String, String> foundVal = searchNode(key);
        int hashValue = hashFunction(key);
        if (foundVal != null){
            array[hashValue].findValueRecursive(array[hashValue].getHead(), key).value = value ;
            return;
        }
        array[hashValue].appendNode(key,value);


    }

    public BucketNode<String,String> delete (String key){
        BucketNode<String, String> foundVal = searchNode(key);
        int hashValue = hashFunction(key);
        if (foundVal != null){
            array[hashValue].deleteNode(key);
            return array[hashValue].deleteNode(key);
        }
        return null;
    }
}
