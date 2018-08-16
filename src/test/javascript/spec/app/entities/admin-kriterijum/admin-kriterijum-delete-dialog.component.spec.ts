/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AkcioniPlanTestModule } from '../../../test.module';
import { AdminKriterijumDeleteDialogComponent } from 'app/entities/admin-kriterijum/admin-kriterijum-delete-dialog.component';
import { AdminKriterijumService } from 'app/entities/admin-kriterijum/admin-kriterijum.service';

describe('Component Tests', () => {
    describe('AdminKriterijum Management Delete Component', () => {
        let comp: AdminKriterijumDeleteDialogComponent;
        let fixture: ComponentFixture<AdminKriterijumDeleteDialogComponent>;
        let service: AdminKriterijumService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AkcioniPlanTestModule],
                declarations: [AdminKriterijumDeleteDialogComponent]
            })
                .overrideTemplate(AdminKriterijumDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdminKriterijumDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdminKriterijumService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
