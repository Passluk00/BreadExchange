import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ActivatedRoute, RouterLink} from "@angular/router";
import {NavbarComponent} from "../../../components/navbar/navbar.component";
import {FooterComponent} from "../../../components/footer/footer.component";
import {NgForOf, NgIf} from "@angular/common";
import {FrontEndControllerService} from "../../../../services/services/front-end-controller.service";
import {ListCategoryFrontEnd} from "../../../../services/models/list-category-front-end";
import {BakeryControllerService} from "../../../../services/services/bakery-controller.service";
import {ItemRequest} from "../../../../services/models/item-request";
import {faArrowLeft} from "@fortawesome/free-solid-svg-icons";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {Item} from "../../../../services/models/item";
import {Category} from "../../../../services/models/category";

@Component({
  selector: 'app-menu',
  imports: [
    NavbarComponent,
    FooterComponent,
    NgForOf,
    NgIf,
    RouterLink,
    FaIconComponent,
    ReactiveFormsModule,
    FormsModule
  ],
  templateUrl: './menu.component.html',
  standalone: true,
  styleUrl: './menu.component.scss'
})
export class MenuComponent implements OnInit{

  logoAzz: string | undefined;              // Logo Azz
  userId: number | undefined;               // BakeryId
  currentItem: number | undefined;          // ItemId
  currentCat: number | undefined            // CategoryId
  data:ListCategoryFrontEnd = {}
  isOwner: boolean = false;
  file:any;
  itemreq: ItemRequest = {
    name:"",
    description:"",
    price:0
  }
  itemMod: ItemRequest = {
    name:"",
    description:"",
    price:0
  }
  isDragging = false;
  uploadFile: File | null = null;
  previewImage: string | null = null;
  toShow:any;
  catName: string = ""



  constructor(
    private route : ActivatedRoute,
    private frontEndService: FrontEndControllerService,
    private bakeryService: BakeryControllerService,
    private cdr: ChangeDetectorRef
  ) {
  }



  ngOnInit() {
    this.route.params.subscribe(params =>{
      this.userId = +params['id']
      this.getData()
      this.checkOwner()
    })
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

  fillDataCat(idCat: number | undefined){
    if(idCat != undefined){
      this.currentCat = idCat
    }
  }

  newCategory(){
    if(this.userId != undefined) {
      this.bakeryService.newCategory({
        bakeryId: this.userId,
        nomeCat: this.catName
      }).subscribe({
        next: () => {
          console.error("invio dati avvenuto")
          this.cdr.detectChanges()
          window.location.reload()
        },
        error: () => {
          console.error("invio dati fallito")
        }
      })
    }
  }

  delCat(){
    if(this.currentCat) {
      this.bakeryService.delCategory({
        idCat: this.currentCat
      }).subscribe({
        next: () => {
          console.error("del cat")
          this.cdr.detectChanges()
          window.location.reload()
        },
        error: () => {
          console.error("del cat fallito")
        }
      })
    }
  }

  scrollToSection(name: String | undefined){
    if(name) {
      const section = document.getElementById('section-' + name)
      if (section) {
        section.scrollIntoView({behavior: 'smooth', block: 'start'})
      }
    }
  }

  getData(){
    if(this.userId){
      this.frontEndService.getAllCategory({
        owner:this.userId
      }).subscribe({
        next: (res) => {
          this.data = res
        },
        error: (err) => {
          console.error("Errore nel Fetch Dati: " + err)
        }
      })
    }
  }

  modItem(){

    if(this.userId != undefined  && this.currentItem != undefined && this.itemMod != undefined){

      this.bakeryService.updateItem({
        idItem: this.currentItem,
        idBac: this.userId,
        body: this.itemMod

      }).subscribe({
        next: () => {
          console.error("Modifica Dati Avvenuta")
          this.cdr.detectChanges()
          window.location.reload()
        },
        error: () => {
          console.error("Modifica Dati Fallita")
        }
      })

    }else{
      console.error("uno dei dati è mancante")
    }
  }

  sendReq(){
    if(this.userId != undefined && this.uploadFile != undefined && this.currentCat != undefined)
      this.bakeryService.newItem({
        idBac:this.userId,                          // preso automaticamente da path
        idCat:this.currentCat,
        body:{
          file:this.uploadFile,
          request:this.itemreq
        }
      }).subscribe({
        next:() => {
          console.error("invio dati avvenuto")
          this.cdr.detectChanges()
          window.location.reload()
        },
        error: (err) => {
          console.error("invio dati fallito"+ err)
        }
      })
  }

  caricaDati(item: Item | undefined, cat: Category | undefined){
    if(item != undefined && cat != undefined){
      if( item.name != undefined &&
          item.description != undefined &&
          item.price != undefined &&
          cat.id != undefined){

        this.toShow = item.img
        this.itemMod.name = item.name;
        this.itemMod.description = item.description;
        this.itemMod.price = item.price
        this.currentItem = item.id
        this.currentCat = cat.id


        console.error("idItem: "+ this.currentItem)
        console.error("idCat: "+ this.currentCat)
        console.error("idBac: "+ this.userId)

      }
    }
  }

  delItem(){

    if(this.userId && this.currentItem && this.currentCat) {

      this.bakeryService.delItem({
        idItem: this.currentItem,
        idCat: this.currentCat,
        idBac: this.userId
      }).subscribe({
        next: (res) => {
          console.error("del avvenuto")
          this.cdr.detectChanges()
          window.location.reload()
        },
        error: (err) => {
          console.error("del fallita")
        }
      })

    }
  }


  // Gestione DragAndDrop


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
    this.toShow = null;
  }

  protected readonly faArrowLeft = faArrowLeft;
}
