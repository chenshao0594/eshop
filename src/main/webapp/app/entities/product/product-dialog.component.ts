import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService, DataUtils } from 'ng-jhipster';

import { Product } from './product.model';
import { ProductPopupService } from './product-popup.service';
import { ProductService } from './product.service';
import { TaxClass, TaxClassService } from '../tax-class';
import { Manufacturer, ManufacturerService } from '../manufacturer';
import { ProductType, ProductTypeService } from '../product-type';
import { MerchantStore, MerchantStoreService } from '../merchant-store';
import { ProductDescription } from '../product-description';
import { Attachment, AttachmentService } from '../attachment';

@Component({
    selector: 'jhi-product-dialog',
    templateUrl: './product-dialog.component.html'
})
export class ProductDialogComponent implements OnInit {

    product: Product;
    authorities: any[];
    isSaving: boolean;
    taxclasses: TaxClass[];
    manufacturers: Manufacturer[];
    producttypes: ProductType[];
    descriptions: ProductDescription[];
    merchantstores: MerchantStore[];
    id: Number;
    attachment: Attachment;
        constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private productService: ProductService,
        private taxClassService: TaxClassService,
        private manufacturerService: ManufacturerService,
        private productTypeService: ProductTypeService,
        private merchantStoreService: MerchantStoreService,
        private attachmentService: AttachmentService,
        private eventManager: EventManager,
        private dataUtils: DataUtils,
        private route: ActivatedRoute,
    ) {
        this.jhiLanguageService.setLocations(['product']);
       
    }
    load(id) {
         this.productService.find(id).subscribe((product) => {
                this.product = product;
                if (product.dateAvailable) {
                    product.dateAvailable = {
                        year: product.dateAvailable.getFullYear(),
                        month: product.dateAvailable.getMonth() + 1,
                        day: product.dateAvailable.getDate()
                    };
                }
         });
    }
    ngOnInit() {
        this.route.params.subscribe((params) => {
            if ( params['id'] ) {
               this.id = params['id'];
            } else {
                this.product = new Product();
            }
        });
        
        this.load(this.id);
        
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.taxClassService.query().subscribe(
            (res: Response) => { this.taxclasses = res.json(); }, (res: Response) => this.onError(res.json()));
        this.manufacturerService.query().subscribe(
            (res: Response) => { this.manufacturers = res.json(); }, (res: Response) => this.onError(res.json()));
        this.productTypeService.query().subscribe(
            (res: Response) => { this.producttypes = res.json(); }, (res: Response) => this.onError(res.json()));
        this.merchantStoreService.query().subscribe(
            (res: Response) => { this.merchantstores = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.product.id !== undefined) {
            this.productService.update(this.product)
                .subscribe((res: Product) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.productService.create(this.product)
                .subscribe((res: Product) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Product) {
        this.eventManager.broadcast({ name: 'productListModification', content: 'OK'});
        this.isSaving = false;
        this.product = result;
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackTaxClassById(index: number, item: TaxClass) {
        return item.id;
    }

    trackManufacturerById(index: number, item: Manufacturer) {
        return item.id;
    }

    trackProductTypeById(index: number, item: ProductType) {
        return item.id;
    }

    trackMerchantStoreById(index: number, item: MerchantStore) {
        return item.id;
    }
    setFileData(event, isImage) {
        if (event.target.files && event.target.files[0]) {
            const file = event.target.files[0];
            if (isImage && !/^image\//.test(file.type)) {
                return;
            }
            let attachment = new Attachment();
            this.dataUtils.toBase64(file, (base64Data) => {
                attachment['content'] = base64Data;
                attachment['contentContentType'] = file.type;
            });
            attachment['boName'] = "Product";
            attachment['boId'] = 1;
            this.attachmentService.create(attachment)
                                   .subscribe((res: Attachment) =>
                                       this.onCreateAttachmentSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }
    private onCreateAttachmentSuccess(result: Product) {
      //  this.eventManager.broadcast({ name: 'productListModification', content: 'OK'});
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-product-popup',
    template: ''
})
export class ProductPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productPopupService: ProductPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.productPopupService
                    .open(ProductDialogComponent, params['id']);
            } else {
                this.modalRef = this.productPopupService
                    .open(ProductDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
