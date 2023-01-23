class ValidationTestApplicationTests {

    private static ObjectMapper mapper;

    private static File resource;

    @BeforeAll
    public static void init() throws FileNotFoundException {
        resource = ResourceUtils.getFile("classpath:data.yml");
        mapper = new ObjectMapper(new YAMLFactory());
    }

    @Test
    public void testList() throws IOException {
        ObjectReader reader = mapper.readerForListOf(User.class)
                .at("/users");
        List<User> userList = reader.readValue(resource);

        Assertions.assertAll(() -> {
            Assertions.assertEquals(3, userList.size());
            Assertions.assertNotNull(userList.get(0));
            Assertions.assertNotNull(userList.get(1));
            Assertions.assertNotNull(userList.get(2));
        });
    }

    @Test
    public void testMap() throws IOException {
        ObjectReader reader = mapper.readerForMapOf(User.class)
                .at("/mapUsers");

        Map<String, User> userMap = reader.readValue(resource);

        Assertions.assertAll(() -> {
            Assertions.assertEquals(2, userMap.size());
            Assertions.assertNotNull(userMap.get("first"));
            Assertions.assertNotNull(userMap.get("second"));
        });
    }

    @Test
    public void testSingle() throws IOException {
        ObjectReader reader = mapper.readerFor(User.class)
                .at("/singleUser");

        User singleUser = reader.readValue(resource);

        Assertions.assertAll(() -> {
            Assertions.assertNotNull(singleUser);
            Assertions.assertEquals(DataUtil.getTestUser(), singleUser);
        });
    }

    public static class DataUtil {

        public static User getTestUser() {
            return new User(
                    "Gosha",
                    "Redkin",
                    List.of("Main", "LEGAL"),
                    new Address(
                            "Lodz",
                            "55-234",
                            "+48 765123000")
            );
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class User {
        private String name;
        private String lastName;
        private List<String> departments;
        private Address address;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Address {
        private String city;
        private String postCode;
        private String phone;
    }
}
