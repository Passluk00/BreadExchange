import {ChangeDetectorRef, Component, Inject, OnInit, PLATFORM_ID} from '@angular/core';
import {isPlatformBrowser, NgForOf, NgIf} from "@angular/common";
import {AuthenticationService} from "../../../../services/services/authentication.service";
import {InfoComponent} from "../../../components/info/info.component";
import {OrderComponent} from "../../../components/order/order.component";
import {FavouriteComponent} from "../../../components/favourite/favourite.component";
import {faArrowLeft, faPencil} from "@fortawesome/free-solid-svg-icons";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {UserControllerService} from "../../../../services/services/user-controller.service";
import {Router} from "@angular/router";
import {CodeInputModule} from "angular-code-input";
import {FormsModule} from "@angular/forms";
import {EmailVerifyRequest} from "../../../../services/models/email-verify-request";
import {TokenVerifyRequest} from "../../../../services/models/token-verify-request";
import {ChangePasswordRequest} from "../../../../services/models/change-password-request";
import {Address} from "../../../../services/models/address";
import {NewAddress} from "../../../../services/models/new-address";
import {BakeryRegisterRequest} from "../../../../services/models/bakery-register-request";
import {AdminControllerService} from "../../../../services/services/admin-controller.service";
import {UserFrontEndResponse} from "../../../../services/models/user-front-end-response";

@Component({
  selector: 'app-user',
  imports: [
    NgForOf,
    NgIf,
    InfoComponent,
    OrderComponent,
    FavouriteComponent,
    FaIconComponent,
    CodeInputModule,
    FormsModule
  ],
  templateUrl: './user.component.html',
  standalone: true,
  styleUrl: './user.component.scss'
})

export class UserComponent implements OnInit{

  constructor(
    private authService: AuthenticationService,
    private cdr: ChangeDetectorRef,
    private userService: UserControllerService,
    private router: Router,
    private adminService: AdminControllerService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {
  }


  isLogged: boolean = true;
  userData:UserFrontEndResponse = {}
  visibleElement: string | null = 'element1';
  isOwner: boolean = false



  ngOnInit() {
    this.checkLoginStatus();
    this.cdr.detectChanges();
    this.checkIsOwner();
  }

  fetchMod(){
    this.userService.getCurrentUser().subscribe({
      next: (data) => {
        this.userData = data;
      },
      error: (err) =>{
        console.log("errore: " + err);
      }
    })
  }

  checkIsOwner(){
    this.userService.checkIfOwner().subscribe({
      next: (res) => {
        this.isOwner = res
      },
      error: () => {
        console.error("errore controllo se gia owner")
      }
    })
  }


  checkLoginStatus(): void{
    if(isPlatformBrowser(this.platformId)) {
      const toc = this.getToken()

      if(toc) {

        this.authService.check({
          token: toc
        }).subscribe({
          next: (res) => {
            this.isLogged = res;
            this.fetchMod()
            this.cdr.detectChanges();

          },
          error: (err) => {
            console.error("Errore nel verificare se sei loggato: "+ err)
          }
        })
      }
    }
  }



  getToken(): string | null{
    if(typeof localStorage.getItem('token') !== 'undefined' && typeof window !=='undefined'){
      return localStorage.getItem("token");
    }
    return null
  }

  showElement(element: string, event:Event){
    event.preventDefault()
    this.visibleElement = element
  }


  // dati placeholder


  test:any = {
    username:"Passluk",
    email:"admin@mail.com",
    url_p:"public/testImg/profile.jpeg",
    url_c:"public/testImg/bread.jpeg",
    address : {
      name: "Casa",
      country: "Italia",
      state: "Lazio",
      provincia: "Viterbo",
      city: "Pascia Romana",
      postalCode: "01014",
      street: "strada cerquabella",
      number: "13"
    }
  }


  info: any;                           // va fatta la funzione che prende dal db i dati
  favBakery = [] //["1" ,"3"]           // va fatta la funzione che prende del db i dati
  order3:any = {
    "id": 1234,
    "customer": "string",
    "totalAmmount": 6,
    "status": "Consegnato",
    "createdAt": "2024-09-02T21:31:28.869Z",
    "delivered": true,
    "deliveryAddress": {
      "id": 0,
      "name": "string",
      "country": "string",
      "state": "string",
      "provincia": "string",
      "city": "string",
      "postalCode": "string",
      "number": 0,
      "street": "string"
    },
    "orderedLineItem": [
      {
        "id": 0,
        "order": "test",
        "itemDesc": {
          "id": 0,
          "name": "string",
          "description": "lorem ipsum",
          "ingredientsItems": [
            {
              "id": 0,
              "name": "string"
            }
          ],
          "price": 0,
          "category": {
            "id": 0,
            "name": "string"
          },
          "bakery": "string",
          "img": "string"
        },
        "quantity": 0,
        "totalPrice": 3
      },
      {
        "id": 0,
        "order": "string",
        "itemDesc": {
          "id": 0,
          "name": "string",
          "description": "string",
          "ingredientsItems": [
            {
              "id": 0,
              "name": "string"
            }
          ],
          "price": 0,
          "category": {
            "id": 0,
            "name": "string"
          },
          "bakery": "string",
          "img": "string"
        },
        "quantity": 0,
        "totalPrice": 3
      }
    ],
    "totalItem": 0,
    "preorderDay": "2024-09-02T21:31:28.869Z"
  }
  order4:any = {
    "id": 1234,
    "customer": "string",
    "totalAmmount": 6,
    "status": "Consegnato",
    "createdAt": "2024-09-02T21:31:28.869Z",
    "delivered": true,
    "deliveryAddress": {
      "id": 0,
      "name": "string",
      "country": "string",
      "state": "string",
      "provincia": "string",
      "city": "string",
      "postalCode": "string",
      "number": 0,
      "street": "string"
    },
    "orderedLineItem": [
      {
        "id": 0,
        "order": "test",
        "itemDesc": {
          "id": 0,
          "name": "string",
          "description": "lorem ipsum",
          "ingredientsItems": [
            {
              "id": 0,
              "name": "string"
            }
          ],
          "price": 0,
          "category": {
            "id": 0,
            "name": "string"
          },
          "bakery": "string",
          "img": "string"
        },
        "quantity": 0,
        "totalPrice": 3
      },
      {
        "id": 0,
        "order": "string",
        "itemDesc": {
          "id": 0,
          "name": "string",
          "description": "string",
          "ingredientsItems": [
            {
              "id": 0,
              "name": "string"
            }
          ],
          "price": 0,
          "category": {
            "id": 0,
            "name": "string"
          },
          "bakery": "string",
          "img": "string"
        },
        "quantity": 0,
        "totalPrice": 3
      }
    ],
    "totalItem": 0,
    "preorderDay": "2024-09-02T21:31:28.869Z"
  }
  orders = []// = [this.order3, this.order4]       // va fatta la funzione che prende tutti gli ordini fatti da un utente



  protected readonly faPencil = faPencil;
  protected readonly faArrowLeft = faArrowLeft;



  // nuovo metodo upload file


  isDragging = false;
  uploadFile: File | null = null;
  previewImage: string | null = null;


  // Gestione Aggiornamento Immagini

  ricaricaPagina() {
    window.location.reload()
  }

  uploadImageProfile(){
    if(this.uploadFile){
      console.log("uploadingThisFile: " + this.uploadFile)
      this.userService.uploadProfilePicture({
        'direction': 1,
        body:{
          file: this.uploadFile
        }
      }).subscribe({
        next:() => {
          this.cdr.detectChanges()
          window.location.reload()
        },
        error:() => {
          console.log("Upload fallito ")
        }
      })

      this.removeImage();
      this.cdr.detectChanges()
    }
  }

  uploadImageBackground(){
    if(this.uploadFile){
      console.log("uploadingThisFile: " + this.uploadFile)
      this.userService.uploadProfilePicture({
        body:{
          file: this.uploadFile
        },
        'direction': 2,
      }).subscribe({
        next:() => {
          this.cdr.detectChanges()
          window.location.reload()
        },
        error:() => {
          console.log("Upload fallito ")
        }
      })

      this.removeImage();
      this.cdr.detectChanges()
    }
  }



  newAddress: NewAddress = {
    name:'',
    city:'',
    country:'',
    number:'',
    postalCode:'',
    provincia:'',
    state:'',
    street:'',
    telNumber:''
  }

  uploadAddress() {
    this.userService.addAddress({
      body: this.newAddress
    }).subscribe({
      next: (res)=> {
        console.log("upload New Address Eseguito")
        this.cdr.detectChanges()
        window.location.reload()
      },
      error: (err) => {
        console.log("upload New Address Fallito")
      }
    })
  };



  // Gestione verifica identità

  message = '';
  isOkay = true;
  submitted = false;



  // Step-1

  emailVerifyRequest: EmailVerifyRequest = {email:'', cemail:''}
  errorMessage: Array<String> = []

  verificaEmail(){
    this.errorMessage = []
    this.userService.verificaEmail({
      body: this.emailVerifyRequest
    }).subscribe({
      error:(err) => {
        console.error(err);
        if(err.error.validationErrors){
          this.errorMessage = err.error.validationErrors;
        }
        else{
          this.errorMessage.push(err.error.error)
        }
      }
    });

  }


  // Step-2

  tokenVerifyRequest: TokenVerifyRequest = {token:''}

  onCodeCompleted(token: string) {
    this.tokenVerifyRequest.token = token;
    this.userService.verifyToken({// TODO creare backend per verificare il codice
      body: this.tokenVerifyRequest                               // TODO cambiare questa in string
    }).subscribe({
      next: (res) => {
        this.message = 'Identità Confermata!';
        this.submitted = true;

        if(res != undefined){
          localStorage.setItem("verifyKey", <string>res.token )
        }

      },
      error: () => {
        this.message = 'Token Scaduto o Invalido';
        this.submitted = true;
        this.isOkay = false;
      }
    });
  }


  // Step-3

  changePasswordRequest: ChangePasswordRequest = {confirmNewPwd:'', newPwd:'', key:''}

  uploadNewPassword(){
    var toc = localStorage.getItem("verifyKey");
    if(toc){
      this.changePasswordRequest.key = toc
    }

    this.authService.forgotPwd({
      body: this.changePasswordRequest
    }).subscribe({
      next:(res) => {
        localStorage.removeItem("token")
        this.router.navigate(["/"])
      },
      error: () => {

      }
    })
  }




  // Become Bakery


  bakeryRegisterRequest: BakeryRegisterRequest = {
    name:"",
    description:"",
    email_azz:"",
    phone_azz:"",
    twitter:"",
    facebook:"",
    instagram:"",
    address:{}
  }

  address1: Address = {
    name:'',
    city:'',
    country:'',
    number:'',
    postalCode:'',
    provincia:'',
    state:'',
    street:'',
    telNumber:''
  }

  isFilled(){
    return (this.bakeryRegisterRequest.name &&
              this.bakeryRegisterRequest.description &&
              this.bakeryRegisterRequest.email_azz &&
              this.bakeryRegisterRequest.phone_azz)
  }

  isFilled2(){
    return (this.address1.name &&
            this.address1.city &&
            this.address1.country &&
            this.address1.number &&
            this.address1.postalCode &&
            this.address1.provincia &&
            this.address1.state &&
            this.address1.street &&
            this.address1.telNumber)
  }

  requestBakery(){

    this.bakeryRegisterRequest.address = this.address1

    console.log("Richiesta: " + this.bakeryRegisterRequest.name)

    this.userService.sendRequest({
      body: this.bakeryRegisterRequest
    }).subscribe({
      next: (res) => {
        console.log("Request Inviata")
      },
      error: (err) => {
        console.log("Invio Request Fallito:" + err)
      }
    })
  }














  // Gestione Drag And Drop


  triggerFileSelect(){
    const fileInput = document.getElementById('fileInput') as HTMLInputElement;
    fileInput.click()
  }

  onFileSelect(event: Event){
    const target = event.target as HTMLInputElement;
    const files = target.files;

    if(files && files.length > 0){
      this.handleFile(files[0])
    }
  }

  onDragOver(event: DragEvent){
    event.preventDefault();
    this.isDragging = true;
  }

  onDragLeave(event: DragEvent){
    event.preventDefault()
    this.isDragging = false;
  }

  onFileDrop(event: DragEvent){
    event.preventDefault()
    this.isDragging = false;

    if(event.dataTransfer?.files && event.dataTransfer.files.length > 0){
      this.handleFile(event.dataTransfer.files[0])
    }
  }

  handleFile(file: File){
    if(file.type.startsWith('image/')){
      this.uploadFile = file;
      this.generatePreview(file);
    }
    else{
      console.log("Inserire un immagine")
    }
  }

  generatePreview(file: File) {
    const reader = new FileReader();
    reader.onload = (event: ProgressEvent<FileReader>) => {
      if (event.target && event.target.result) {
        this.previewImage = event.target.result as string; // Assegna il risultato come stringa
      }
    };
    reader.readAsDataURL(file); // Legge il file
  }

  removeImage(){
    this.uploadFile = null;
    this.previewImage = null;
  }




  id1:number = 1;
  id2:number = 2;



  navigateToUser(id: number) {
    this.router.navigate(['/bakery', id])
      .then(success => {
        if (!success) {
          // Navigazione fallita
          this.router.navigate(['/404']);
        }
      })
      .catch(error => {
        console.error('Errore durante la navigazione:', error);
        this.router.navigate(['/404']);
      });
  }


}

