import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];

    void clear() {
        storage = new Resume[10000];
    }

    void save(Resume r) {
        if (this.size() < 10000) storage[this.size()] = r;
        else System.out.println("Переполнение");
    }

    Resume get(String uuid) {
        return Arrays.stream(storage).filter(o -> o != null && o.uuid.equals(uuid)).findFirst().orElse(null);
    }

    void delete(String uuid) {
        storage = Arrays.copyOf(Arrays.stream(storage).filter(o -> o != null && !o.uuid.equals(uuid)).toArray(Resume[]::new), 10000);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.stream(storage).filter(Objects::nonNull).toArray(Resume[]::new);
    }

    int size() {
        return (int)Arrays.stream(storage).filter(Objects::nonNull).count();
    }
}
