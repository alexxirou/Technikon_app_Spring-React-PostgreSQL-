package com.scytalys.technikon.dto;


import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class UserSearchResponseDto{ String tin;
                                    String username;
                                    String email;
                                    String name;
                                    String surname;
                                    String address;
                                    String phoneNumber;
                                    boolean isActive;
                                    String[] propertyTin;
                                    long version;
}
