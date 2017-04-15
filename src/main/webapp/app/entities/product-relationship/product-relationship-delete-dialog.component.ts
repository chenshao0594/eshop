import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { ProductRelationship } from './product-relationship.model';
import { ProductRelationshipPopupService } from './product-relationship-popup.service';
import { ProductRelationshipService } from './product-relationship.service';

@Component({
    selector: 'jhi-product-relationship-delete-dialog',
    templateUrl: './product-relationship-delete-dialog.component.html'
})
export class ProductRelationshipDeleteDialogComponent {

    productRelationship: ProductRelationship;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private productRelationshipService: ProductRelationshipService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['productRelationship']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productRelationshipService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'productRelationshipListModification',
                content: 'Deleted an productRelationship'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-product-relationship-delete-popup',
    template: ''
})
export class ProductRelationshipDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productRelationshipPopupService: ProductRelationshipPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.productRelationshipPopupService
                .open(ProductRelationshipDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
