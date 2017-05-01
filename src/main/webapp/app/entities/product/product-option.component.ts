import { Component, OnInit, OnDestroy, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { Response } from '@angular/http';
import { NgbActiveModal, NgbModalRef, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import { EventManager, AlertService, JhiLanguageService, DataUtils } from 'ng-jhipster';

import { ProductService } from './product.service';
import { ProductOptionService } from '../product-option/product-option.service';
import { Product } from './product.model';

import { ProductOption } from '../product-option/product-option.model';



const URL = '/api/attachments';

@Component( {
    templateUrl: './product-options.component.html',
    styleUrls: [ './fileupload.style.scss' ]
} )
export class ProductOptionDialogComponent  implements OnInit {
    product: Product;
    productId: number;
    boId: number;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;
    optionIds: number[];

    constructor(
        public activeModal: NgbActiveModal,
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private productService: ProductService,
        private dataUtils: DataUtils,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['product']);
        this.jhiLanguageService.setLocations(['productOption']);
        this.isSaving = false;
        this.optionIds = new Array();
    }

    ngOnInit() {
    }
    loadOptions(productId){
//        this.attachmentService.queryAttachmentsByBO('products',this.boId).subscribe(
//                (res: Response) => { this.attachments = res.json(); }, 
//                (res: Response) => this.onError(res.json()));
    }
    
    registerChangeInProductAttachments() {
        this.eventSubscriber = this.eventManager.subscribe('attachmentListModification', (response) => this.loadOptions(this.boId));
    }


    clear() {
        this.activeModal.dismiss('cancel');
    }
    
    updateCheckedOptions(optionId) {
        var index = this.optionIds.indexOf(optionId, 0);
        if (index > -1) {
           this.optionIds.splice(index, 1);
        }else{
            this.optionIds.push(optionId);
        }
    }
    save() {
        this.isSaving = true;
        this.productService.addOptionValue(this.productId, this.optionIds)
                            .subscribe((res: Product) =>
                            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        this.activeModal.dismiss('cancel');
    }
    private onSaveSuccess(result: Product) {
        this.isSaving = false;
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
    back() {
        window.history.back();
    }
}


@Component({
    selector: 'jhi-product-option-popup-componnet',
    template: ''
})
export class ProductOptionDialogPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;
    isOpen: boolean;
    constructor(
        private route: ActivatedRoute,
        private modalService: NgbModal,
        private router: Router,
        private productOptionService: ProductOptionService,
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
                this.modalRef = this.open(ProductOptionDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
    open(component: Component, productId?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;
        this.productOptionService.query().subscribe(
                (res: Response) => this.productModalRef(component,res.json(), productId)
                );
       };
       
    productModalRef(component: Component, productOptions: ProductOption[], productId: number): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.productOptions = productOptions;
        modalRef.componentInstance.productId = productId;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}

