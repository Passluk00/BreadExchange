import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {CaroselloInfinitoComponent} from "../../components/carosello-infinito/carosello-infinito.component";
import {FrontEndControllerService} from "../../../../services/services/front-end-controller.service";
import {BakeryfrontEndResponse} from "../../../../services/models/bakeryfront-end-response";
import {NavbarComponent} from "../../../components/navbar/navbar.component";
import {FooterComponent} from "../../../components/footer/footer.component";
import {AddressComponent} from "../../components/address/address.component";
import {Address} from "../../../../services/models/address";
import {OrariComponent} from "../../components/orari/orari.component";
import {NgForOf, NgIf} from "@angular/common";
import {faArrowLeft, faPencil} from "@fortawesome/free-solid-svg-icons";
import {CodeInputModule} from "angular-code-input";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {FormsModule} from "@angular/forms";
import {BakeryControllerService} from "../../../../services/services/bakery-controller.service";
import {NewAddress} from "../../../../services/models/new-address";
import {Week} from "../../../../services/models/week";
import {WeekDay} from "../../../../services/models/week-day";
import {ModifyWeekRequest} from "../../../../services/models/modify-week-request";

@Component({
  selector: 'app-bakery',
  imports: [
    CaroselloInfinitoComponent,
    NavbarComponent,
    FooterComponent,
    AddressComponent,
    OrariComponent,
    NgIf,
    CodeInputModule,
    FaIconComponent,
    FormsModule,
  ],
  standalone: true,
  templateUrl: '/bakery.component.html',
  styleUrl: './bakery.component.scss'
})
export class BakeryComponent implements OnInit{


  userId: number | undefined;
  data: BakeryfrontEndResponse = {}
  toPass: Address = {}
  isOwner: boolean = false;
  isDragging = false;
  uploadFile: File | null = null;
  previewImage: string | null = null;
  newAddress: NewAddress = {
    name:"",
    city:"",
    country:"",
    number:"",
    provincia:"",
    state:"",
    telNumber:"",
    street:"",
    postalCode:""
  }
  currentWeek: Week = {}
  lun: WeekDay = {
    dayOfWeek:"Lunedi"
  }
  mar: WeekDay = {
    dayOfWeek:"Martedi"
  }
  mer: WeekDay = {
    dayOfWeek:"Mercoledi"
  }
  gio: WeekDay = {
    dayOfWeek:"Giovedi"
  }
  ven: WeekDay = {
    dayOfWeek:"Venerdi"
  }
  sab: WeekDay = {
    dayOfWeek:"Sabato"
  }
  dom: WeekDay = {
    dayOfWeek:"Domenica"
  }
  newWeek: ModifyWeekRequest = {}


  constructor(
    private route: ActivatedRoute,
    private frontEndService: FrontEndControllerService,
    private bakeryService: BakeryControllerService
  ) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params =>{
      this.userId = +params['id']
      this.checkOwner()
      this.getData()
      this.getWeek()
    })
  }



  getData(){
    if(this.userId) {
      this.frontEndService.bakeryDetail({
        id: this.userId
      }).subscribe({
        next: (res) => {
          this.data = res;
          if(this.data.address) {
            this.toPass = this.data.address;
          }
        },
        error: (err) => {
          console.error("Errore nel Fetch Dati: " + err)
        }
      })
    }
  }

  getWeek(){

    if(this.userId != null){

      this.frontEndService.getWeek({
        idBakery: this.userId
      }).subscribe({
        next: (res) => {
          this.currentWeek = res

          this.aggiornaDati(res);

        },
        error: (err) => {
          console.error("errore nel fetch della week")
        }
      })

    }

  }

  aggiornaDati(res: Week){

    if( res.lun != undefined &&
        res.mar != undefined &&
        res.mer != undefined &&
        res.gio != undefined &&
        res.ven != undefined &&
        res.sab != undefined &&
        res.dom !=undefined) {

      this.lun = res.lun
      this.mar = res.mar
      this.mer = res.mer
      this.gio = res.gio
      this.ven = res.ven
      this.sab = res.sab
      this.dom = res.dom

    }

  }

  updateOrari(){

    if(this.lun != null &&
      this.mar != null &&
      this.mer != null &&
      this.gio != null &&
      this.ven != null &&
      this.sab != null &&
      this.dom != null &&
      this.userId != null
      ){

      this.newWeek.lun = this.lun
      this.newWeek.mar = this.mar
      this.newWeek.mer = this.mer
      this.newWeek.gio = this.gio
      this.newWeek.ven = this.ven
      this.newWeek.sab = this.sab
      this.newWeek.dom = this.dom

      console.error("lunedi: "+ this.newWeek.lun.dayOfWeek)

      this.bakeryService.updateWeek({
        body: this.newWeek,
        IdBakery: this.userId
      }).subscribe({
        next: () => {
          console.error("ok update week")
        },
        error: (err) => {
          console.error("fail update week")
        }
      })


    }

  }

  uploadAddress(){
    if(this.newAddress != undefined && this.userId != undefined){

      this.bakeryService.addAddress1({
        idBac: this.userId,
        body: this.newAddress
      }).subscribe({
        next: () => {},
        error: () => {}
      })

    }
  }

  uploadImageProfile(){
    if(this.uploadFile != undefined && this.userId != undefined){
      this.bakeryService.uploadProfilePicture1({
        idBac: this.userId,
        body: {
          file: this.uploadFile
        }
      }).subscribe({
        next: () => {
          console.error("upload pic Avvenuto")
        },
        error: () => {
          console.error("upload pic Fallito")
        }
      })
    }
  }

  uploadImageBack(){
    if(this.uploadFile != undefined && this.userId != undefined){
      this.bakeryService.uploadBackPicture({
        idBac: this.userId,
        body: {
          file: this.uploadFile
        }
      }).subscribe({
        next: () => {
          console.error("upload pic Avvenuto")
        },
        error: () => {
          console.error("upload pic Fallito")
        }
      })
    }
  }

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

  checkOwner(){
    if(this.userId){
      this.frontEndService.checkOwner({
        idBack: this.userId
      }).subscribe({
        next:(res) => {
          this.isOwner = res;
          console.error("valore owner: "+this.isOwner)
        },
        error:() => {
          this.isOwner = false
        }
      })
    }
  }






  protected readonly faArrowLeft = faArrowLeft;
  protected readonly faPencil = faPencil;
}
