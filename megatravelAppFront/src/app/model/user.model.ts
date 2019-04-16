export class  UserModel{
  constructor(public username: string,
              public password: string,
              public rePassword: string,
              public firstName: string,
              public lastName: string,
              public email: string,
              public city: string,){}
}
