import org.junit.jupiter.api.Test;

class DataUnloadTest {

    @Test
    public void assertAllCorrectOutput() {
        System.out.println(DataUnload.firstname());
        System.out.println(DataUnload.lastname());
        System.out.println(DataUnload.fullName());
        System.out.println(DataUnload.email());
        System.out.println(DataUnload.domain());
        System.out.println(DataUnload.url());
        System.out.println(DataUnload.street());
        DataUnload.feee();
    }
}