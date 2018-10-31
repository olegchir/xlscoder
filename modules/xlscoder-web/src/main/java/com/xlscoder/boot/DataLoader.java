package com.xlscoder.boot;

import com.xlscoder.model.Key;
import com.xlscoder.model.Role;
import com.xlscoder.model.User;
import com.xlscoder.repository.KeyRepository;
import com.xlscoder.repository.RoleRepository;
import com.xlscoder.repository.UserRepository;
import com.xlscoder.security.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired private KeyRepository keyRepository;

    @Override
    public void run(String... strings) throws Exception {
        Key testKey = new Key();
        testKey.setKeyName("test");
        testKey.setPrivateKey("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCsh1rwe7KI/63A107b81O9Y9mIZ+6hbidv8haNhTl5F2ZWPmET9WdNdyjU8XbWHb9bTFXDtXgeVMYozJf5joPIjqHW3kEwRGW5tWb8w0MSFPsE6kcTUT//fkRyNy/bmfYlx0GtAzdx4YD5PcfB8JD/kD+8srJNKNbJZ6S0pvidri5uWFS9VMwy5ewAh6JXyDaRNoZQ0dv2Uh2R8Hvj+rn7/LyNCc0sJG+8+PHEnabsLnkaDqzx0qbCN+1dM6vL157fjErsoYoXlSyin/5mdTJE7ho08exaVaaH3rkKqUl2CPpeEkSI/D9K7jPWWPx55DMVGWoyFE2r7RVpq3goC6uhAgMBAAECggEABDj73M7qO7gmRIYAhBmcfxImjNQeXw0XIgJG0wfjtDcCZjH4pkhnUtoLwcp3Ih5wRIfFwZh7bCKfBvia75yxVQNIqr2d0tSZuMXb02vkBFlHG4OwYaSncFViz3jSTwmhoKMT1GwzuzHNukvsy65bniWlqbSl8IWDXEKRIWrEwvcqFH0FKExyEwdzB+v7L5sh0tyhB+D9sKWaCettPm7GIzilbqdo0ltTnNJfyDuD+7rDsaA9UJrog4e84vyuWoFFGIs5hde8wd0/dBbPNq2qqRBFX41d/Wn2DhnCVQ+zqwJiv1Mam22R01dyoO6l71KA5hr2ko49uySEAReGSW9jAQKBgQDgcZeoTyLYLUUzXHRHwr1cyf4EOtG4Z7PxR2IlgAfak9/79yt16WFwJhEI3IvA5u2vU3XqA+acMowITlbouZT6tSFh/BpH4BoKybHqoI6VZQhupPo9kHqorzPUyefXK2pq7SuGvUw303U1QaxW/UJvBFkERHp7ZTSQwWY9VU72KQKBgQDEyS9+QUBB/blKTCIt9bW2/HSJBv6cl6smnzrXDR94TiHBVeTO/7Sjorfu3+H72MPAxpt77oZbP9SvorjMI46s0Fn7SG1OuqwUOj6gn3TDYano4JuBqAcgT3C6TnnULElfSanI4gDoChoyOxgUQTC7q0RwieFG4iH9so9iQEaIuQKBgQCd+VX1cT3lSnfansh5eegu1z2jXIMgeF1/Be9e1a6xekO85UwBwjKC7UgwJIt5SxEgxm3IONaoOiu17O3fAECL9dF5VihpTqMF1NEVg8zX+jTlK9m2W2r6L9cbfsFgAX71o9lvDO4InR1yTrcuwzNvUHAXQNu03pcRDA8aPGFHgQKBgGVhDFGsBhG5SBMJw/YPhs09pD/P1a4QyQC9uY9+2D6fae5zdMxbmdFPjBjJSF/53WdcKlAfoyIxcT4Gw9OPYfqP4Dt/paiQrQRCuW8AlyPtFZ6+z/5s9TblFjs1ILh5FFe92HWAUV05jyNfpFkS+KtGYZzku6VL7J0Jt6qzWGMpAoGABoIbqktHwppSqYVGn/iaoWNug+Gi10L5/gtwwH1Zad5pvE1/8zaLZeaITEhpzEovjR0HjuCXspThSynzgP5aKvpWC2D8rp+dscTRJ3nN2Fg/wvA75/zvrAn6RO7KwEGYAD14RgX00DYV/qy50AVxntjE1ym05st5LoWn8U0B6vE=");
        testKey.setPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArIda8HuyiP+twNdO2/NTvWPZiGfuoW4nb/IWjYU5eRdmVj5hE/VnTXco1PF21h2/W0xVw7V4HlTGKMyX+Y6DyI6h1t5BMERlubVm/MNDEhT7BOpHE1E//35Ecjcv25n2JcdBrQM3ceGA+T3HwfCQ/5A/vLKyTSjWyWektKb4na4ublhUvVTMMuXsAIeiV8g2kTaGUNHb9lIdkfB74/q5+/y8jQnNLCRvvPjxxJ2m7C55Gg6s8dKmwjftXTOry9ee34xK7KGKF5Usop/+ZnUyRO4aNPHsWlWmh965CqlJdgj6XhJEiPw/Su4z1lj8eeQzFRlqMhRNq+0Vaat4KAuroQIDAQAB");
        testKey.setPgpPrivateKey("lQO+BFvWOAMBCACsh1rwe7KI/63A107b81O9Y9mIZ+6hbidv8haNhTl5F2ZWPmET9WdNdyjU8XbWHb9bTFXDtXgeVMYozJf5joPIjqHW3kEwRGW5tWb8w0MSFPsE6kcTUT//fkRyNy/bmfYlx0GtAzdx4YD5PcfB8JD/kD+8srJNKNbJZ6S0pvidri5uWFS9VMwy5ewAh6JXyDaRNoZQ0dv2Uh2R8Hvj+rn7/LyNCc0sJG+8+PHEnabsLnkaDqzx0qbCN+1dM6vL157fjErsoYoXlSyin/5mdTJE7ho08exaVaaH3rkKqUl2CPpeEkSI/D9K7jPWWPx55DMVGWoyFE2r7RVpq3goC6uhABEBAAH+AwMCZ/6QAKKOHqxgxcrK3MAqaxtisiCurGnbc3ao9yF1eRF3r4ka9QfWFA1UiChbJkhB7JfUl0i74PsixYg157D4EoIGHuQupD7k+RAQ9vurP97FJibx87PVUvgU6kJ0AhNOX22pANzQC4IEndvQmYtMJCY5NpJt8SCTGKwfvHgjzBUciJeS3RXM21F4Pq9ypp53k9G3ONFE1JVgJfYlOuSqOqkCbbz8Pb9xlBv+o5a+ThIp2fnfYtlMg3CZD5TaamS3+X4S+G5OEj4Xh2bY1VqAPgxueceiyi/5PoG6y1ospd5YjVXgawEKSh9zsa8RydeTiv6hlvbvKBcpfB4lmGAK3xhqPP5fQHz3hswXdo+jmvaEZQXxZHrdvc5U+nGaBzGTUpIOUH6R8D4re6QiFGvAXHJNZn44Iabk2ZcmZU/oLbWqVmY4gn7SnYEWwrOrEZFBp3DomS6GIay78cTrDwbZ0ZkPdw4a53ffITvZQ54jAnqL1iyDhnmDUAruV4HCO1aTMrT9zF38igRdnP5tlyGOjdnOW1BOB9WrsW1MTIArPRZALcAwGL2kU4KLu27D60yvB/odvdZHccz725rvjo7OTzubKuiAg81JkjHG7uOTxTnL+J7sJNZX35O+9+mgeahegm9R4Y0O24EplEXGopM9UxmRZIO1VSLvyKmNvZke6GZoVs9pfmeoWrBO4M5yGcw7aF40+Sy4rywylLCJU92ekcefNjwVvukZ56z+2IG4WprRhXJZ7evGnV5/vExIvnvTyi9X0aOJNrFw6g3tXeVVQ8CiMdxVU1EPYS9VKUxZqFF5LgndsjCtSjuEd6XzlysXDf4OKq5J0STNuGQQXRLye61qN84w5gIkfHEw5Va8OoS0RikCPNiQ5nwE0Ug1XS6yqQdOoTf9gaPC095ltLQIeGxzY29kZXKJARwEEAECAAYFAlvWOAMACgkQmRBje9cF+KQ+tQf/bf1/1HnqRgSQBcgM/Gm7x+xZoLDYjiAfLc0/ntANiWrgXzH+T1IDahRqEQW88y7+ZOyv6eBVRNucTwTVrDPkqAIG1XgXLAJhbmEO8M+1USGVkoeDt05RL7QYAaYiOmPe5nrny5s2AXi9PldWHZ37vCdBkk+6ka+VB+xsG6FMCMR8ABkMsbbpyp9RPd6x43/fGfl38v0m6R4rqEPfSm+ZbTkwxjXJrgIfgxRm2FEzSmhbhXsPArNZDDBHMD67Jis5awHWG27sX+1Hu4GldGiigp+bVe5pJSUn5F5+YqKdaNtU1/slKIPww4tzw+y8vfIu2vpiyKQ9DJnoZK0O19HRVA==");
        testKey.setPgpPublicKey("mQENBFvWOAMBCACsh1rwe7KI/63A107b81O9Y9mIZ+6hbidv8haNhTl5F2ZWPmET9WdNdyjU8XbWHb9bTFXDtXgeVMYozJf5joPIjqHW3kEwRGW5tWb8w0MSFPsE6kcTUT//fkRyNy/bmfYlx0GtAzdx4YD5PcfB8JD/kD+8srJNKNbJZ6S0pvidri5uWFS9VMwy5ewAh6JXyDaRNoZQ0dv2Uh2R8Hvj+rn7/LyNCc0sJG+8+PHEnabsLnkaDqzx0qbCN+1dM6vL157fjErsoYoXlSyin/5mdTJE7ho08exaVaaH3rkKqUl2CPpeEkSI/D9K7jPWWPx55DMVGWoyFE2r7RVpq3goC6uhABEBAAG0CHhsc2NvZGVyiQEcBBABAgAGBQJb1jgDAAoJEJkQY3vXBfikPrUH/239f9R56kYEkAXIDPxpu8fsWaCw2I4gHy3NP57QDYlq4F8x/k9SA2oUahEFvPMu/mTsr+ngVUTbnE8E1awz5KgCBtV4FywCYW5hDvDPtVEhlZKHg7dOUS+0GAGmIjpj3uZ658ubNgF4vT5XVh2d+7wnQZJPupGvlQfsbBuhTAjEfAAZDLG26cqfUT3eseN/3xn5d/L9JukeK6hD30pvmW05MMY1ya4CH4MUZthRM0poW4V7DwKzWQwwRzA+uyYrOWsB1htu7F/tR7uBpXRoooKfm1XuaSUlJ+RefmKinWjbVNf7JSiD8MOLc8PsvL3yLtr6YsikPQyZ6GStDtfR0VQ=");
        testKey.setShaSalt("salted!");
        testKey.setPgpPassword("xlscoder");
        testKey.setPgpIdentity("xlscoder");
        keyRepository.save(testKey);

        Key testKey2 = new Key();
        testKey2.setKeyName("test2");
        testKey2.setPrivateKey("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCsh1rwe7KI/63A107b81O9Y9mIZ+6hbidv8haNhTl5F2ZWPmET9WdNdyjU8XbWHb9bTFXDtXgeVMYozJf5joPIjqHW3kEwRGW5tWb8w0MSFPsE6kcTUT//fkRyNy/bmfYlx0GtAzdx4YD5PcfB8JD/kD+8srJNKNbJZ6S0pvidri5uWFS9VMwy5ewAh6JXyDaRNoZQ0dv2Uh2R8Hvj+rn7/LyNCc0sJG+8+PHEnabsLnkaDqzx0qbCN+1dM6vL157fjErsoYoXlSyin/5mdTJE7ho08exaVaaH3rkKqUl2CPpeEkSI/D9K7jPWWPx55DMVGWoyFE2r7RVpq3goC6uhAgMBAAECggEABDj73M7qO7gmRIYAhBmcfxImjNQeXw0XIgJG0wfjtDcCZjH4pkhnUtoLwcp3Ih5wRIfFwZh7bCKfBvia75yxVQNIqr2d0tSZuMXb02vkBFlHG4OwYaSncFViz3jSTwmhoKMT1GwzuzHNukvsy65bniWlqbSl8IWDXEKRIWrEwvcqFH0FKExyEwdzB+v7L5sh0tyhB+D9sKWaCettPm7GIzilbqdo0ltTnNJfyDuD+7rDsaA9UJrog4e84vyuWoFFGIs5hde8wd0/dBbPNq2qqRBFX41d/Wn2DhnCVQ+zqwJiv1Mam22R01dyoO6l71KA5hr2ko49uySEAReGSW9jAQKBgQDgcZeoTyLYLUUzXHRHwr1cyf4EOtG4Z7PxR2IlgAfak9/79yt16WFwJhEI3IvA5u2vU3XqA+acMowITlbouZT6tSFh/BpH4BoKybHqoI6VZQhupPo9kHqorzPUyefXK2pq7SuGvUw303U1QaxW/UJvBFkERHp7ZTSQwWY9VU72KQKBgQDEyS9+QUBB/blKTCIt9bW2/HSJBv6cl6smnzrXDR94TiHBVeTO/7Sjorfu3+H72MPAxpt77oZbP9SvorjMI46s0Fn7SG1OuqwUOj6gn3TDYano4JuBqAcgT3C6TnnULElfSanI4gDoChoyOxgUQTC7q0RwieFG4iH9so9iQEaIuQKBgQCd+VX1cT3lSnfansh5eegu1z2jXIMgeF1/Be9e1a6xekO85UwBwjKC7UgwJIt5SxEgxm3IONaoOiu17O3fAECL9dF5VihpTqMF1NEVg8zX+jTlK9m2W2r6L9cbfsFgAX71o9lvDO4InR1yTrcuwzNvUHAXQNu03pcRDA8aPGFHgQKBgGVhDFGsBhG5SBMJw/YPhs09pD/P1a4QyQC9uY9+2D6fae5zdMxbmdFPjBjJSF/53WdcKlAfoyIxcT4Gw9OPYfqP4Dt/paiQrQRCuW8AlyPtFZ6+z/5s9TblFjs1ILh5FFe92HWAUV05jyNfpFkS+KtGYZzku6VL7J0Jt6qzWGMpAoGABoIbqktHwppSqYVGn/iaoWNug+Gi10L5/gtwwH1Zad5pvE1/8zaLZeaITEhpzEovjR0HjuCXspThSynzgP5aKvpWC2D8rp+dscTRJ3nN2Fg/wvA75/zvrAn6RO7KwEGYAD14RgX00DYV/qy50AVxntjE1ym05st5LoWn8U0B6vE=");
        testKey2.setPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArIda8HuyiP+twNdO2/NTvWPZiGfuoW4nb/IWjYU5eRdmVj5hE/VnTXco1PF21h2/W0xVw7V4HlTGKMyX+Y6DyI6h1t5BMERlubVm/MNDEhT7BOpHE1E//35Ecjcv25n2JcdBrQM3ceGA+T3HwfCQ/5A/vLKyTSjWyWektKb4na4ublhUvVTMMuXsAIeiV8g2kTaGUNHb9lIdkfB74/q5+/y8jQnNLCRvvPjxxJ2m7C55Gg6s8dKmwjftXTOry9ee34xK7KGKF5Usop/+ZnUyRO4aNPHsWlWmh965CqlJdgj6XhJEiPw/Su4z1lj8eeQzFRlqMhRNq+0Vaat4KAuroQIDAQAB");
        testKey2.setPgpPrivateKey("lQO+BFvWOAMBCACsh1rwe7KI/63A107b81O9Y9mIZ+6hbidv8haNhTl5F2ZWPmET9WdNdyjU8XbWHb9bTFXDtXgeVMYozJf5joPIjqHW3kEwRGW5tWb8w0MSFPsE6kcTUT//fkRyNy/bmfYlx0GtAzdx4YD5PcfB8JD/kD+8srJNKNbJZ6S0pvidri5uWFS9VMwy5ewAh6JXyDaRNoZQ0dv2Uh2R8Hvj+rn7/LyNCc0sJG+8+PHEnabsLnkaDqzx0qbCN+1dM6vL157fjErsoYoXlSyin/5mdTJE7ho08exaVaaH3rkKqUl2CPpeEkSI/D9K7jPWWPx55DMVGWoyFE2r7RVpq3goC6uhABEBAAH+AwMCZ/6QAKKOHqxgxcrK3MAqaxtisiCurGnbc3ao9yF1eRF3r4ka9QfWFA1UiChbJkhB7JfUl0i74PsixYg157D4EoIGHuQupD7k+RAQ9vurP97FJibx87PVUvgU6kJ0AhNOX22pANzQC4IEndvQmYtMJCY5NpJt8SCTGKwfvHgjzBUciJeS3RXM21F4Pq9ypp53k9G3ONFE1JVgJfYlOuSqOqkCbbz8Pb9xlBv+o5a+ThIp2fnfYtlMg3CZD5TaamS3+X4S+G5OEj4Xh2bY1VqAPgxueceiyi/5PoG6y1ospd5YjVXgawEKSh9zsa8RydeTiv6hlvbvKBcpfB4lmGAK3xhqPP5fQHz3hswXdo+jmvaEZQXxZHrdvc5U+nGaBzGTUpIOUH6R8D4re6QiFGvAXHJNZn44Iabk2ZcmZU/oLbWqVmY4gn7SnYEWwrOrEZFBp3DomS6GIay78cTrDwbZ0ZkPdw4a53ffITvZQ54jAnqL1iyDhnmDUAruV4HCO1aTMrT9zF38igRdnP5tlyGOjdnOW1BOB9WrsW1MTIArPRZALcAwGL2kU4KLu27D60yvB/odvdZHccz725rvjo7OTzubKuiAg81JkjHG7uOTxTnL+J7sJNZX35O+9+mgeahegm9R4Y0O24EplEXGopM9UxmRZIO1VSLvyKmNvZke6GZoVs9pfmeoWrBO4M5yGcw7aF40+Sy4rywylLCJU92ekcefNjwVvukZ56z+2IG4WprRhXJZ7evGnV5/vExIvnvTyi9X0aOJNrFw6g3tXeVVQ8CiMdxVU1EPYS9VKUxZqFF5LgndsjCtSjuEd6XzlysXDf4OKq5J0STNuGQQXRLye61qN84w5gIkfHEw5Va8OoS0RikCPNiQ5nwE0Ug1XS6yqQdOoTf9gaPC095ltLQIeGxzY29kZXKJARwEEAECAAYFAlvWOAMACgkQmRBje9cF+KQ+tQf/bf1/1HnqRgSQBcgM/Gm7x+xZoLDYjiAfLc0/ntANiWrgXzH+T1IDahRqEQW88y7+ZOyv6eBVRNucTwTVrDPkqAIG1XgXLAJhbmEO8M+1USGVkoeDt05RL7QYAaYiOmPe5nrny5s2AXi9PldWHZ37vCdBkk+6ka+VB+xsG6FMCMR8ABkMsbbpyp9RPd6x43/fGfl38v0m6R4rqEPfSm+ZbTkwxjXJrgIfgxRm2FEzSmhbhXsPArNZDDBHMD67Jis5awHWG27sX+1Hu4GldGiigp+bVe5pJSUn5F5+YqKdaNtU1/slKIPww4tzw+y8vfIu2vpiyKQ9DJnoZK0O19HRVA==");
        testKey2.setPgpPublicKey("mQENBFvWOAMBCACsh1rwe7KI/63A107b81O9Y9mIZ+6hbidv8haNhTl5F2ZWPmET9WdNdyjU8XbWHb9bTFXDtXgeVMYozJf5joPIjqHW3kEwRGW5tWb8w0MSFPsE6kcTUT//fkRyNy/bmfYlx0GtAzdx4YD5PcfB8JD/kD+8srJNKNbJZ6S0pvidri5uWFS9VMwy5ewAh6JXyDaRNoZQ0dv2Uh2R8Hvj+rn7/LyNCc0sJG+8+PHEnabsLnkaDqzx0qbCN+1dM6vL157fjErsoYoXlSyin/5mdTJE7ho08exaVaaH3rkKqUl2CPpeEkSI/D9K7jPWWPx55DMVGWoyFE2r7RVpq3goC6uhABEBAAG0CHhsc2NvZGVyiQEcBBABAgAGBQJb1jgDAAoJEJkQY3vXBfikPrUH/239f9R56kYEkAXIDPxpu8fsWaCw2I4gHy3NP57QDYlq4F8x/k9SA2oUahEFvPMu/mTsr+ngVUTbnE8E1awz5KgCBtV4FywCYW5hDvDPtVEhlZKHg7dOUS+0GAGmIjpj3uZ658ubNgF4vT5XVh2d+7wnQZJPupGvlQfsbBuhTAjEfAAZDLG26cqfUT3eseN/3xn5d/L9JukeK6hD30pvmW05MMY1ya4CH4MUZthRM0poW4V7DwKzWQwwRzA+uyYrOWsB1htu7F/tR7uBpXRoooKfm1XuaSUlJ+RefmKinWjbVNf7JSiD8MOLc8PsvL3yLtr6YsikPQyZ6GStDtfR0VQ=");
        testKey2.setShaSalt("salted!");
        testKey2.setPgpPassword("xlscoder");
        testKey2.setPgpIdentity("xlscoder");
        keyRepository.save(testKey2);

        Role adminRole = new Role();
        adminRole.setName(Roles.ROLE_ADMIN.toString());
        adminRole.setHumanReadableName("Администратор");
        roleRepository.save(adminRole);

        Role producerRole = new Role();
        producerRole.setName(Roles.ROLE_PRODUCER.toString());
        producerRole.setHumanReadableName("Исчтоник данных");
        roleRepository.save(producerRole);

        Role consumerRole = new Role();
        consumerRole.setName(Roles.ROLE_CONSUMER.toString());
        consumerRole.setHumanReadableName("Потребитель данных");
        roleRepository.save(consumerRole);

        Role noobRole = new Role();
        noobRole.setName(Roles.ROLE_NOOB.toString());
        noobRole.setHumanReadableName("Новозарегистрированный пользователь");
        roleRepository.save(noobRole);


        User adminUser = new User();
        adminUser.setLogin("admin");
        adminUser.setPassword(bCryptPasswordEncoder.encode("admin"));
        adminUser.getRoles().add(adminRole);
        adminUser.getKeys().add(testKey2);
        adminUser.setUserName("Admin");
        adminUser.setUserSurname("Admin");
        adminUser.setUserEmail("oleg@graalvm.io");
        userRepository.save(adminUser);

        User producerUser = new User();
        producerUser.setLogin("producer");
        producerUser.setPassword(bCryptPasswordEncoder.encode("producer"));
        producerUser.getRoles().add(producerRole);
        producerUser.getKeys().add(testKey);
        producerUser.setUserName("Vladimir");
        producerUser.setUserSurname("Putin");
        producerUser.setUserEmail("v@fsb.ru");
        userRepository.save(producerUser);

        User consumerUser = new User();
        consumerUser.setLogin("consumer");
        consumerUser.setPassword(bCryptPasswordEncoder.encode("producer"));
        consumerUser.getRoles().add(consumerRole);
        consumerUser.setUserName("Dmitriy");
        consumerUser.setUserSurname("Medvedev");
        consumerUser.setUserEmail("medvedko@fsb.ru");
        userRepository.save(consumerUser);

    }
}
