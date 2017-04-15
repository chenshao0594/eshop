import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { TaxClass } from './tax-class.model';
import { TaxClassPopupService } from './tax-class-popup.service';
import { TaxClassService } from './tax-class.service';

@Component({
    selector: 'jhi-tax-class-delete-dialog',
    templateUrl: './tax-class-delete-dialog.component.html'
})
export class TaxClassDeleteDialogComponent {

    taxClass: TaxClass;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private taxClassService: TaxClassService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['taxClass']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.taxClassService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'taxClassListModification',
                content: 'Deleted an taxClass'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tax-class-delete-popup',
    template: ''
})
export class TaxClassDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private taxClassPopupService: TaxClassPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.taxClassPopupService
                .open(TaxClassDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
