import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { ProductAttribute } from './product-attribute.model';
import { ProductAttributePopupService } from './product-attribute-popup.service';
import { ProductAttributeService } from './product-attribute.service';

@Component({
    selector: 'jhi-product-attribute-delete-dialog',
    templateUrl: './product-attribute-delete-dialog.component.html'
})
export class ProductAttributeDeleteDialogComponent {

    productAttribute: ProductAttribute;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private productAttributeService: ProductAttributeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['productAttribute']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productAttributeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'productAttributeListModification',
                content: 'Deleted an productAttribute'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-product-attribute-delete-popup',
    template: ''
})
export class ProductAttributeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productAttributePopupService: ProductAttributePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.productAttributePopupService
                .open(ProductAttributeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
