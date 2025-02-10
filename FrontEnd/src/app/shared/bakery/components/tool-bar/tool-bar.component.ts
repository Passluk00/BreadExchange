import {ChangeDetectorRef, Component, Input, OnInit} from '@angular/core';
import {UserControllerService} from "../../../../services/services/user-controller.service";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {NgIf} from "@angular/common";
import {MatFabButton} from "@angular/material/button";
import {BakeryfrontEndResponse} from "../../../../services/models/bakeryfront-end-response";
import {Address} from "../../../../services/models/address";
import {NewAddress} from "../../../../services/models/new-address";
import {Week} from "../../../../services/models/week";
import {WeekDay} from "../../../../services/models/week-day";
import {ModifyWeekRequest} from "../../../../services/models/modify-week-request";
import {BakeryControllerService} from "../../../../services/services/bakery-controller.service";
import {FrontEndControllerService} from "../../../../services/services/front-end-controller.service";
import {faArrowLeft, faPencil} from "@fortawesome/free-solid-svg-icons";
import {FormsModule} from "@angular/forms";
import {ContactInfo} from "../../../../services/models/contact-info";
import {ReqModDesc} from "../../../../services/models/req-mod-desc";
import {UpdateSocialBakery} from "../../../../services/models/update-social-bakery";

@Component({
  selector: 'app-tool-bar',
  imports: [
    FaIconComponent,
    NgIf,
    MatFabButton,
    FormsModule
  ],
  templateUrl: './tool-bar.component.html',
  standalone: true,
  styleUrl: './tool-bar.component.scss'
})
export class ToolBarComponent implements OnInit{

  @Input() userId!: number | undefined;
  @Input() data!: BakeryfrontEndResponse;



  isInFav: boolean = false;
  toPass: Address = {}
  isOwner: boolean = false;
  isDragging = false;
  uploadFile: File | null = null;
  previewImage: string | null = null;
  info: ContactInfo = {}
  newDesc: ReqModDesc = {}
  updateSocial: UpdateSocialBakery = {}
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
  isOpen: boolean = false;




  constructor(
    private userService: UserControllerService,
    private bakeryService: BakeryControllerService,
    private frontEndService: FrontEndControllerService,
  ) {
  }



  ngOnInit(): void {
    this.checkOwner()
    this.checkFav()
    this.getWeek()
    this.getInfo()
    this.checkOpen()
    this.setDesc();
    this.setSocial();
    if(this.data.address != undefined && this.data.info != undefined){
      this.toPass = this.data.address;
    }
  }




  setSocial(){
    this.updateSocial.facebook = this.data.info?.facebook
    this.updateSocial.instagram = this.data.info?.instagram
    this.updateSocial.twitter = this.data.info?.twitter
  }

  modSocial(){
    if(this.userId != null){
      this.bakeryService.updateSocial({
        idBac: this.userId,
        body: this.updateSocial
      }).subscribe({
        next:() => {
          console.error("Aggiornamento dati social avvenuto")
          window.location.reload()
        },
        error: (err) => {
          console.error("errore invio dati update Social: "+err)
        }
      })
    }
  }

  setDesc(){
    if(this.data.desc != undefined){
      this.newDesc.desc = this.data.desc
    }
  }

  updateDesc(){
    if(this.userId != undefined){
      this.bakeryService.updateDesc({
        idBac: this.userId,
         body: this.newDesc
      }).subscribe({
        next:() => {
          console.log("Invio dati avvenuto");
          window.location.reload()
        },
        error: (err) => {
          console.error("Invio Dati Fallito: "+err)
        }
      })
    }
  }

  checkOpen(){

    if(this.userId != null){
      this.frontEndService.isOpen({
        idBac: this.userId
      }).subscribe({
        next:(res) => {
          this.isOpen = res
        },
        error:(err) => {
          console.error("Errore nel fetch dati status open: " + err)
        }
      })
    }
  }

  changeStatus(){
    if(this.userId != null){
      this.bakeryService.changeStatus({
        idBac: this.userId
      }).subscribe({
        next:(res) => {
          this.isOpen = !this.isOpen
          console.error("status apertura: "+ this.isOpen)
        },
        error:(err) => {
          console.error("errore modifica status: "+err)
        }
      })

    }
  }

  redirectToFacebook(){
    if(this.info.facebook != undefined){
      window.open(this.info.facebook);
    }
  }

  redirectToInstagram(){
    if(this.info.instagram != undefined){
      window.open(this.info.instagram);
    }
  }

  redirectToX(){
    if(this.info.twitter != undefined){
      window.open(this.info.twitter);
    }
  }

  getInfo(){
    if(this.userId != null){

      this.frontEndService.getInfo({
        idBac: this.userId
      }).subscribe({
        next: (res) => {
          this.info = res
        },
        error: (err) => {
          console.error("errore di fetch dati info:"+err)
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

  checkFav(){
    if(this.userId != null){
      this.userService.isToFav({
        idBac: this.userId
      }).subscribe({
        next: (res) => {
          this.isInFav = res
        },
        error: (err) =>{
          console.error("error in fetch data fav: "+err)
        }
      })

    }
  }

  addToFavourite(){

    if(this.userId != null){

      this.userService.addToFav({
        idBac: this.userId
      }).subscribe({
        next: () => {
          console.error("Added to fav list")
          window.location.reload()
        },
        error: (err) => {
          console.error("fail to add to fav list "+err)
        }
      })
    }
  }

  remToFavourite(){

    if(this.userId != null){

      this.userService.remToFav({
        idBac: this.userId
      }).subscribe({
        next: () => {
          console.error("Added to fav list")
          window.location.reload()
        },
        error: (err) => {
          console.error("fail to add to fav list "+err)
        }
      })
    }
  }


  protected readonly faArrowLeft = faArrowLeft;
  protected readonly faPencil = faPencil;
}
