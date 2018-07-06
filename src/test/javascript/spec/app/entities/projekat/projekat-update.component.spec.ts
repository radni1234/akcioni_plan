/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AkcioniPlanTestModule } from '../../../test.module';
import { ProjekatUpdateComponent } from 'app/entities/projekat/projekat-update.component';
import { ProjekatService } from 'app/entities/projekat/projekat.service';
import { Projekat } from 'app/shared/model/projekat.model';

describe('Component Tests', () => {
    describe('Projekat Management Update Component', () => {
        let comp: ProjekatUpdateComponent;
        let fixture: ComponentFixture<ProjekatUpdateComponent>;
        let service: ProjekatService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AkcioniPlanTestModule],
                declarations: [ProjekatUpdateComponent]
            })
                .overrideTemplate(ProjekatUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProjekatUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProjekatService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Projekat(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.projekat = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Projekat();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.projekat = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
