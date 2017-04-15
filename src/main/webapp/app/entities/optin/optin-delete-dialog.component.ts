import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Optin } from './optin.model';
import { OptinPopupService } from './optin-popup.service';
import { OptinService } from './optin.service';

@Component({
    selector: 'jhi-optin-delete-dialog',
    templateUrl: './optin-delete-dialog.component.html'
})
export class OptinDeleteDialogComponent {

    optin: Optin;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private optinService: OptinService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['optin']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.optinService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'optinListModification',
                content: 'Deleted an optin'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-optin-delete-popup',
    template: ''
})
export class OptinDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private optinPopupService: OptinPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.optinPopupService
                .open(OptinDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
