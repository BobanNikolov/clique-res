export enum Role {
    ROLE_ADMIN,
    ROLE_MANAGER,
    ROLE_HOST
  }

export interface SignUpRequest {
    firstName: string;
    lastName: string;
    email: string;
    username: string;
    password: string;
    role: Role;
}

export interface SignInRequest {
    username: string;
    password: string;  
}

export interface JwtAuthenticationToken{
    token: string;
}